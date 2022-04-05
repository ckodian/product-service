package au.twc.core.product.service.impl;

import au.twc.core.product.domain.Product;
import au.twc.core.product.dto.ProductDto;
import au.twc.core.product.dto.VariantProductDto;
import au.twc.core.product.populators.ProductConverter;
import au.twc.core.product.repository.ProductRepository;
import au.twc.core.product.service.ProductService;
import au.twc.core.product.service.VariantProductService;
import au.twc.core.product.web.rest.errors.BadRequestAlertException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final TenantResolverService tenantResolverService;

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductConverter productConverter;

    private final ProductRepository productRepository;

    private final AmazonDynamoDB amazonDynamoDB;

    private final VariantProductService variantProductService;

    public ProductServiceImpl(TenantResolverService tenantResolverService,
                              ProductConverter productConverter,
                              ProductRepository productRepository,
                              AmazonDynamoDB amazonDynamoDB,
                              VariantProductService variantProductService) {
        this.tenantResolverService = tenantResolverService;
        this.productConverter = productConverter;
        this.productRepository = productRepository;
        this.amazonDynamoDB = amazonDynamoDB;
        this.variantProductService = variantProductService;
    }

    /**
     * This method looks for a product by an ID
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ProductDto> findProductById(String id) {
        String tenant = tenantResolverService.resolveTenant();
        Optional<Product> prodOp = productRepository.findByIdAndTenantIdAndDeleted(id, tenantResolverService.resolveTenant(), Boolean.FALSE);
        Set<VariantProductDto> variants = variantProductService.findVariantsForProductAndTenant(id, tenant);
        log.debug("variants [{}]", variants);
        if (prodOp.isPresent()) {
            ProductDto product = productConverter.convert(prodOp.get());
            if (!CollectionUtils.isEmpty(variants)) {
                product.setVariants(variants);
            }
            return Optional.of(product);
        }
        return Optional.empty();
    }

    /**
     * This method looks for a product by an ID
     *
     * @param productRef
     * @return
     */
    @Override
    public Optional<ProductDto> findProductByRef(String productRef) {
        Optional<Product> prodOp = productRepository.findByProductRefAndTenantIdAndDeleted(productRef, tenantResolverService.resolveTenant(), Boolean.FALSE);
        if (prodOp.isPresent()) {
            return Optional.of(productConverter.convert(prodOp.get()));
        }
        return Optional.empty();
    }

    /**
     * This method looks for a product by an ID
     *
     * @param id
     * @return
     */
    @Override
    public Optional<ProductDto> findProductBasicInfo(String id) {
        Optional<Product> prodOp = productRepository.findById(id);
        if (prodOp.isPresent()) {

            ProductDto productDto = new ProductDto();
            Product model = prodOp.get();
            productDto.id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .gtin(model.getGtin())
                .gtinType(model.getGtinType())
                .isbn(model.getIsbn())
                .status(model.getStatus())
                .disabled(model.getDisabled())
                .expirationDate(model.getExpirationDate())
                .link(model.getLink())
                .imageLink(model.getImageLink())
                .additionalImageLink(model.getAdditionalImageLink())
                .mobileLink(model.getMobileLink())
                .availability(model.getAvailability())
                .color(model.getColor());

            if (!CollectionUtils.isEmpty(model.getVariance())) {
                productDto.setVariance(model.getVariance());
            }

            return Optional.of(productDto);
        }
        return Optional.empty();
    }

    /**
     * This method looks for a product by its upc ID
     *
     * @param upc
     * @return
     */
    @Override
    public Optional<ProductDto> findProductByUpc(String upc) {
        return Optional.empty();
    }

    /**
     * This method looks for a product by its isbn
     *
     * @param isbn
     * @return
     */
    @Override
    public Optional<ProductDto> findProductByIsbn(String isbn) {
        return Optional.empty();
    }

    /**
     * This method looks for a product by its mpn ID
     *
     * @param mpn
     * @return
     */
    @Override
    public Optional<ProductDto> findProductByMpn(String mpn) {
        return Optional.empty();
    }

    /**
     * This method looks for a product by its jan ID
     *
     * @param jan
     * @return
     */
    @Override
    public Optional<ProductDto> findProductByJan(String jan) {
        return Optional.empty();
    }

    /**
     * This method looks for a product by its itf ID
     *
     * @param itf
     * @return
     */
    @Override
    public Optional<ProductDto> findProductByItf(String itf) {
        return Optional.empty();
    }

    /**
     * This method looks for a product by its upi ID
     *
     * @param upi
     * @return
     */
    @Override
    public Optional<ProductDto> findProductByUpi(String upi) {
        return Optional.empty();
    }

    /**
     * This method looks for a product by its ean ID
     *
     * @param ean
     * @return
     */
    @Override
    public Optional<ProductDto> findProductByEan(String ean) {
        return Optional.empty();
    }

    /**
     * This method is responsible for creating a product, if the product already exist, then it will update the record.
     *
     * @param productDto
     * @return
     */
    @Override
    public Optional<ProductDto> createProduct(ProductDto productDto) {

        Set<VariantProductDto> variants = productDto.getVariants();
        productDto.setVariants(null);
        productDto.setDeleted(Boolean.FALSE);

        Product model = getModelAndCheck(productDto);

        if(!StringUtils.isEmpty(model.getId())) {
            throw new BadRequestAlertException("provided product reference already exists, product reference must be unique.", "Product", "productRef already in use");
        }
        model = productConverter.reverseConvert(productDto, model);
        saveProductWithReference(model);

        final String prodId = model.getId();
        final String prodRef = model.getProductRef();
        if (!CollectionUtils.isEmpty(variants)) {
            try {
                variantProductService.createOrUpdateVariantProducts(variants, prodId, prodRef);
            } catch (Exception exe) {
                log.error("Error saving variants", exe);
                productRepository.delete(model);
                throw exe;
            }
        }

        return findProductById(model.getId());
    }

    /**
     * This method is responsible for creating a product, if the product already exist, then it will update the record.
     *
     * @param productDto
     * @return
     */
    @Override
    public Optional<ProductDto> updateProduct(ProductDto productDto) {
        Set<VariantProductDto> variants = productDto.getVariants();
        productDto.setVariants(null);
        productDto.setDeleted(Boolean.FALSE);

        Product model = getModelAndCheck(productDto);
        final String prodId = model.getId();
        final String prodRef = model.getProductRef();
        model = productConverter.reverseConvert(productDto, model);

        if (!CollectionUtils.isEmpty(variants)) {
            try {
                variantProductService.createOrUpdateVariantProducts(variants, prodId, prodRef);
            } catch (Exception exe) {
                log.error("Error saving variants", exe);
                throw exe;
            }
        }
        saveProductWithReference(model);
        return findProductById(model.getId());
    }

    private Product getModelAndCheck(ProductDto productDto) {
        Product model = productConverter.getModelItem(productDto);

        String modelRef = StringUtils.isEmpty(model.getProductRef()) ? "" : model.getProductRef();
        String dtoRef = StringUtils.isEmpty(productDto.getProductRef()) ? "" : productDto.getProductRef();

        if (!modelRef.equalsIgnoreCase(dtoRef)) {
            Optional<Product> byRef = productRepository
                .findByProductRefAndTenantIdAndDeleted(
                    dtoRef,
                    tenantResolverService.resolveTenant(),
                    Boolean.FALSE);

            if (byRef.isPresent()) {
                throw new BadRequestAlertException("provided product reference already existing", "Product", "ref already exists");
            }
        }
        return model;
    }

    /**
     * Lookup a product by its particulars
     *
     * @param dto
     * @return
     */
    @Override
    public List<ProductDto> lookupProduct(ProductDto dto) {

        String tenant = tenantResolverService.resolveTenant();
        String indexName = "";
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        DynamoDBQueryExpression<Product> queryExpression = new DynamoDBQueryExpression<>();
        Map<String, AttributeValue> eav = new HashMap<>();
        StringBuffer queryExp = new StringBuffer();
        StringBuffer filterExp = new StringBuffer();
        filterExp.append("tenant_id = :tenant_id");
        eav.put(":tenant_id", new AttributeValue().withS(tenant));

        if (!StringUtils.isEmpty(dto.getProductRef())) {
            eav.put(":product_ref", new AttributeValue().withS(dto.getProductRef()));
            indexName = "product_productRef_index";
            queryExp.append("productRef = :product_ref");
        }

        if (!StringUtils.isEmpty(dto.getGtin())) {
            eav.put(":gtin", new AttributeValue().withS(dto.getGtin()));
            if(StringUtils.isEmpty(indexName)) {
                indexName = "product_gtin_index";
                queryExp.append("gtin = :gtin");
            } else {
                filterExp.append(" and ");
                filterExp.append("gtin = :gtin");
            }

        }

        queryExpression
            .withKeyConditionExpression(queryExp.toString())
            .withFilterExpression(filterExp.toString())
            .withConsistentRead(false)
            .withIndexName(indexName)
            .withExpressionAttributeValues(eav);

        PaginatedQueryList<Product> result = dynamoDBMapper.query(Product.class, queryExpression);

        if(!ObjectUtils.isEmpty(result) && !result.isEmpty()) {
            List<ProductDto> resDtos = result.stream().map(productConverter::convert).collect(Collectors.toList());
            return resDtos;
        }
        return Collections.emptyList();
    }

    /**
     * At times a customer might scan in a product that is not available at present in the system, and this will trigger and event to
     * create an unverified product, which require action by either system or manually to mark as verified or approved.
     *
     * @param productDto
     * @return
     */
    @Override
    public Optional<ProductDto> createAnUnverifiedProduct(ProductDto productDto) {
        return Optional.empty();
    }

    /**
     * find all products
     *
     * @return
     */
    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findByTenantIdAndDeleted(tenantResolverService.resolveTenant(), Boolean.FALSE);
        return products.stream().map(productConverter::convert).collect(Collectors.toList());
    }

    /**
     * Remove a particular product from repository
     *
     * @param id
     */
    @Override
    public void removeProductById(String id) {
        String tenant = tenantResolverService.resolveTenant();
        Optional<Product> product = productRepository.findByIdAndTenantId(id, tenant);
        variantProductService.removeVariantsByBaseProductAndTenantId(id, tenant);
        if (product.isPresent()) {
            Product prod = product.get();
            prod.setDeleted(true);
            productRepository.save(prod);
        }
    }

    @Override
    public void removeProductByProductRef(String productRef) {
        Optional<Product> product = productRepository.findByProductRefAndTenantId(productRef, tenantResolverService.resolveTenant());
        if (product.isPresent()) {
            Product prod = product.get();
            prod.setDeleted(true);
            productRepository.save(prod);
        }
    }

    @Override
    public void uploadProducts(List<ProductDto> productDtoList) {
        List<Product> productList = new ArrayList<>();
        for (ProductDto productDto : productDtoList) {
            productList.add(productConverter.reverseConvert(productDto, null));
        }
        //TODO: saveAll does batching but batching might be disabled by default - https://www.baeldung.com/spring-data-jpa-batch-inserts
        productRepository.saveAll(productList);
    }

    //    /**
//     * Search products
//     *
//     * @param query
//     * @return
//     */
//    @Override
//    public List<ProductDto> searchProduct(String query) {
//
//        return StreamSupport
//            .stream(productSearchRepository.search(queryStringQuery(query)).spliterator(), false)
//            .map(productConverter::convert).collect(Collectors.toList());
//    }

    protected Product saveProductWithReference(Product model) {

        if (!CollectionUtils.isEmpty(model.getVariance())
            || !CollectionUtils.isEmpty(model.getVariantOptions())) {
            model.variantsAvailable(true);
        }

        return productRepository.save(model);
    }
}
