package au.twc.core.product.service.impl;

import au.twc.core.product.domain.VariantProduct;
import au.twc.core.product.dto.VariantProductDto;
import au.twc.core.product.populators.VariantProductConverter;
import au.twc.core.product.repository.ProductRepository;
import au.twc.core.product.repository.VariantProductRepository;
import au.twc.core.product.service.VariantProductService;
import au.twc.core.product.web.rest.errors.BadRequestAlertException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class VariantProductServiceImpl implements VariantProductService {

    private static final Logger log = LoggerFactory.getLogger(VariantProductServiceImpl.class);

    private final TenantResolverService tenantResolverService;

    private final ProductRepository productRepository;

    private final VariantProductRepository variantProductRepository;

    private final VariantProductConverter variantProductConverter;

    private AmazonDynamoDB amazonDynamoDB;

    public VariantProductServiceImpl(TenantResolverService tenantResolverService, ProductRepository productRepository,
                                     VariantProductRepository variantProductRepository,
                                     VariantProductConverter variantProductConverter,
                                     AmazonDynamoDB amazonDynamoDB) {
        this.tenantResolverService = tenantResolverService;
        this.productRepository = productRepository;
        this.variantProductRepository = variantProductRepository;
        this.variantProductConverter = variantProductConverter;
        this.amazonDynamoDB = amazonDynamoDB;
    }

    /**
     * This method looks for a product by an ID
     *
     * @param id
     * @return Optional<VariantProductDto>
     */
    @Override
    public Optional<VariantProductDto> findVariantProductById(String id) {
        String tenantId = tenantResolverService.resolveTenant();
        Optional<VariantProduct> vOpt = variantProductRepository.findByIdAndTenantIdAndDeleted(id, tenantId, Boolean.FALSE);
        if (vOpt.isPresent()) {
            return Optional.of(variantProductConverter.convert(vOpt.get()));
        }

        return Optional.empty();
    }

    /**
     * Find Basic Variant information on variants
     *
     * @param id
     * @return Optional<VariantProductDto>
     */
    @Override
    public Set<VariantProductDto> findVariantProductBasicInfoByBaseProduct(String id) {
        List<VariantProduct> vSet = variantProductRepository.findByBaseProductIdAndTenantIdAndDeleted(id, tenantResolverService.resolveTenant(), Boolean.FALSE);

        Set<VariantProductDto> variantsDto = new HashSet<>();
        if (!CollectionUtils.isEmpty(vSet)) {
            vSet.stream().forEach(vModel -> {
                VariantProductDto dto = new VariantProductDto();
                dto.id(vModel.getId())
                    .title(vModel.getTitle())
                    .description(vModel.getDescription())
                    .gtin(vModel.getGtin())
                    .isbn(vModel.getIsbn())
                    .gtinType(vModel.getGtinType())
                    .status(vModel.getStatus())
                    .disabled(vModel.getDisabled())
                    .expirationDate(vModel.getExpirationDate())
                    .link(vModel.getLink())
                    .imageLink(vModel.getImageLink())
                    .additionalImageLink(vModel.getAdditionalImageLink())
                    .mobileLink(vModel.getMobileLink())
                    .availability(vModel.getAvailability())
                    .color(vModel.getColor());
                variantsDto.add(dto);
            });
        }
        return variantsDto;
    }


    /**
     * This method looks for a product by an ID
     *
     * @param baseProductRef
     * @param productRef
     * @return Optional<VariantProductDto>
     */
    @Override
    public Optional<VariantProductDto> findVariantProductByProductRefAndBaseProductRef(String productRef, String baseProductRef) {
        Optional<VariantProduct> vOpt = variantProductRepository.findByProductRefAndBaseProductRefAndTenantIdAndDeleted(productRef, baseProductRef, tenantResolverService.resolveTenant(), Boolean.FALSE);
        return Optional.of(variantProductConverter.convert(vOpt.get()));
    }

    /**
     * This method looks for a product by an ID
     *
     * @param variantId
     * @param baseProductId
     * @return Optional<VariantProductDto>
     */
    @Override
    public Optional<VariantProductDto> findVariantProductByIdAndBaseProductId(String variantId, String baseProductId) {
        Optional<VariantProduct> vOpt = variantProductRepository.findByBaseProductIdAndIdAndTenantId(baseProductId, variantId, tenantResolverService.resolveTenant());
        if (vOpt.isPresent()) {
            return Optional.of(variantProductConverter.convert(vOpt.get()));
        }
        return Optional.empty();
    }

    /**
     * This method looks for a product by an ID
     *
     * @param variantRef
     * @return Optional<VariantProductDto>
     */
    @Override
    public Optional<VariantProductDto> findVariantProductByProductRef(String variantRef) {
        Optional<VariantProduct> vOpt = variantProductRepository.findByProductRefAndTenantIdAndDeleted(variantRef, tenantResolverService.resolveTenant(), Boolean.FALSE);
        if (vOpt.isPresent()) {
            return Optional.of(variantProductConverter.convert(vOpt.get()));
        }
        return Optional.empty();
    }

    /**
     * This method looks for a product by its upc ID
     *
     * @param upc
     * @return Optional<VariantProductDto>
     */
    @Override
    public Optional<VariantProductDto> findVariantProductByUpc(String upc) {
        return Optional.empty();
    }

    /**
     * This method looks for a product by its isbn
     *
     * @param isbn
     * @return Optional<VariantProductDto>
     */
    @Override
    public Optional<VariantProductDto> findVariantProductByIsbn(String isbn) {
        return Optional.empty();
    }

    /**
     * This method looks for a product by its mpn ID
     *
     * @param mpn
     * @return Optional<VariantProductDto>
     */
    @Override
    public Optional<VariantProductDto> findVariantProductByMpn(String mpn) {
        return Optional.empty();
    }

    /**
     * This method looks for a product by its jan ID
     *
     * @param jan
     * @return Optional<VariantProductDto>
     */
    @Override
    public Optional<VariantProductDto> findVariantProductByJan(String jan) {
        return Optional.empty();
    }

    /**
     * This method looks for a product by its itf ID
     *
     * @param itf
     * @return Optional<VariantProductDto>
     */
    @Override
    public Optional<VariantProductDto> findVariantProductByItf(String itf) {
        return Optional.empty();
    }

    /**
     * This method looks for a product by its ean ID
     *
     * @param ean
     * @return Optional<VariantProductDto>
     */
    @Override
    public Optional<VariantProductDto> findVariantProductByEan(String ean) {
        return Optional.empty();
    }

    /**
     * This method looks for a product by its ean ID
     *
     * @param baseProduct
     * @return Optional<VariantProductDto>
     */
    @Override
    public Set<VariantProductDto> findVariantsForProductAndTenant(String baseProduct, String tenant) {
        List<VariantProduct> variants = variantProductRepository.findByBaseProductIdAndTenantIdAndDeleted(baseProduct, tenant, Boolean.FALSE);

        if (!CollectionUtils.isEmpty(variants)) {
            return variants.stream().map(variantProductConverter::convert).collect(Collectors.toSet());
        }
        return Collections.EMPTY_SET;
    }

    public Set<VariantProductDto> findVariantsForProduct(String baseProduct) {
        return findVariantsForProductAndTenant(baseProduct, tenantResolverService.resolveTenant());
    }

    /**
     * This method is responsible for creating a product, if the product already exist, then it will update the record.
     *
     * @param dto
     * @param productId
     * @return Optional<VariantProductDto>
     */
    @Override
    public Optional<VariantProductDto> createOrUpdateVariantProduct(VariantProductDto dto, String productId) {

        //This is to identify the base product in further methods
        dto.setBaseProductId(productId);

        VariantProduct vProd = variantProductConverter.reverseConvert(dto, null);
        vProd = saveVariantProductWithReference(vProd);

        return findVariantProductById(vProd.getId());
    }

    private VariantProduct getModelAndCheck(VariantProductDto vDto) {
        VariantProduct vProd = variantProductConverter.getModelItem(vDto);

        String modelRef = StringUtils.isEmpty(vProd.getProductRef()) ? "" : vProd.getProductRef();
        String dtoRef = StringUtils.isEmpty(vDto.getProductRef()) ? "" : vDto.getProductRef();

        if (!modelRef.equalsIgnoreCase(dtoRef)) {
            Optional<VariantProduct> byRef = variantProductRepository
                .findByProductRefAndTenantIdAndDeleted(
                    dtoRef,
                    tenantResolverService.resolveTenant(),
                    Boolean.FALSE);

            if (byRef.isPresent()) {
                throw new BadRequestAlertException("provided variant product reference already existing", "VariantProduct", "ref already exists");
            }
        }
        return vProd;
    }

    /**
     * This method is responsible for creating a product, if the product already exist, then it will update the record.
     *
     * @param dtos
     * @return Optional<VariantProductDto>
     */
    @Override
    public void createOrUpdateVariantProducts(Set<VariantProductDto> dtos,
                                              String baseProductId,
                                              String baseProductRef) {
        if (!CollectionUtils.isEmpty(dtos)) {
            List<VariantProduct> vModels = new ArrayList<>();
            dtos
                .forEach(vDto -> {
                    VariantProduct model = this.getModelAndCheck(vDto);
                    if (
                        (!StringUtils.isEmpty(model.getBaseProductId())
                            && !StringUtils.isEmpty(baseProductId)
                            && !model.getBaseProductId().equalsIgnoreCase(baseProductId))
                            ||
                            (!StringUtils.isEmpty(model.getBaseProductRef())
                                && !StringUtils.isEmpty(baseProductRef)
                                && !model.getBaseProductRef().equalsIgnoreCase(baseProductRef))
                    ) {
                        throw new BadRequestAlertException("provided variant product is linked to another base product", "VariantProduct", "incorrect variant to product link");
                    }

                    model.setBaseProductId(baseProductId);
                    model.setBaseProductRef(baseProductRef);
                    model.setDeleted(false);
                    vModels.add(variantProductConverter.reverseConvert(vDto, model));
                });
            variantProductRepository.saveAll(vModels);
        }
    }

    /**
     * This method is responsible for creating a product, if the product already exist, then it will update the record.
     *
     * @param dto
     * @return Optional<VariantProductDto>
     */
    @Override
    public Optional<VariantProductDto> createVariantProduct(VariantProductDto dto) {

        VariantProduct vProd = getModelAndCheck(dto);
        if (!StringUtils.isEmpty(vProd.getId())) {
            throw new BadRequestAlertException("provided reference already exists, product reference must be unique.", "Product", "productRef already in use");
        }

        if (
            (!StringUtils.isEmpty(vProd.getBaseProductId())
                && !StringUtils.isEmpty(dto.getBaseProductId())
                && !vProd.getBaseProductId().equalsIgnoreCase(dto.getBaseProductId()))
                ||
                (!StringUtils.isEmpty(vProd.getBaseProductRef())
                    && !StringUtils.isEmpty(dto.getBaseProductRef())
                    && !vProd.getBaseProductRef().equalsIgnoreCase(dto.getBaseProductRef()))
        ) {
            throw new BadRequestAlertException("provided variant product is linked to another base product", "VariantProduct", "incorrect variant to product link");
        }

        vProd = variantProductConverter.reverseConvert(dto, vProd);
        return Optional.of(variantProductConverter.convert(saveVariantProductWithReference(vProd)));
    }


    /**
     * This method is responsible for creating a product, if the product already exist, then it will update the record.
     *
     * @param dto
     * @return Optional<VariantProductDto>
     */
    @Override
    public Optional<VariantProductDto> createOrUpdateVariantProduct(VariantProductDto dto) {

        VariantProduct vProd = getModelAndCheck(dto);
        if (
            (!StringUtils.isEmpty(vProd.getBaseProductId())
                && !StringUtils.isEmpty(dto.getBaseProductId())
                && !vProd.getBaseProductId().equalsIgnoreCase(dto.getBaseProductId()))
                ||
                (!StringUtils.isEmpty(vProd.getBaseProductRef())
                    && !StringUtils.isEmpty(dto.getBaseProductRef())
                    && !vProd.getBaseProductRef().equalsIgnoreCase(dto.getBaseProductRef()))
        ) {
            throw new BadRequestAlertException("provided variant product is linked to another base product", "VariantProduct", "incorrect variant to product link");
        }

        vProd = variantProductConverter.reverseConvert(dto, vProd);
        return Optional.of(variantProductConverter.convert(saveVariantProductWithReference(vProd)));
    }

    @Override
    public void uploadVariantProducts(List<VariantProductDto> dtos) {
        List<VariantProduct> variantProducts = new ArrayList<>();
        for (VariantProductDto dto : dtos) {
            variantProducts.add(variantProductConverter.reverseConvert(dto, null));
        }
        variantProductRepository.saveAll(variantProducts);
    }

    /**
     * This method is responsible for creating a product, if the product already exist, then it will update the record.
     *
     * @param productId
     * @return Optional<VariantProductDto>
     */
    @Override
    public List<VariantProductDto> findAllVariantsForProduct(String productId) {
        return null;
    }

    /**
     * Lookup a product by its particulars
     *
     * @param dto
     * @return List<VariantProductDto>
     */
    @Override
    public List<VariantProductDto> lookupVariantProduct(VariantProductDto dto) {

        String tenant = tenantResolverService.resolveTenant();
        String indexName = "";
        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        DynamoDBQueryExpression<VariantProduct> queryExpression = new DynamoDBQueryExpression<>();
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
            if (StringUtils.isEmpty(indexName)) {
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

        PaginatedQueryList<VariantProduct> result = dynamoDBMapper.query(VariantProduct.class, queryExpression);

        if (!ObjectUtils.isEmpty(result) && !result.isEmpty()) {
            List<VariantProductDto> resDtos = result.stream().map(variantProductConverter::convert).collect(Collectors.toList());
            return resDtos;
        }
        return Collections.emptyList();
    }

    /**
     * find all products
     *
     * @return List<VariantProductDto>
     */
    @Override
    public List<VariantProductDto> findAll() {
        List<VariantProduct> allVariants = variantProductRepository.findByTenantIdAndDeleted(tenantResolverService.resolveTenant(), Boolean.FALSE);
        return allVariants.stream().map(variantProductConverter::convert).collect(Collectors.toList());
    }

    /**
     * Remove a particular product from repository
     *
     * @param variantId
     */
    @Override
    public void removeVariantById(String variantId) {

        Optional<VariantProduct> vOpt = variantProductRepository.findByIdAndTenantIdAndDeleted(variantId, tenantResolverService.resolveTenant(), Boolean.FALSE);

        if (vOpt.isPresent()) {
            VariantProduct vProd = vOpt.get();
            vProd.deleted(true);
            variantProductRepository.save(vProd);
        }
    }

    /**
     * Remove a particular product from repository
     *
     * @param productId
     * @param variantId
     */
    @Override
    public void removeVariantByBaseProductIdAndVariantId(String productId, String variantId) {

        //TODO check if changing from findById to findByProductRef is ok
        Optional<VariantProduct> vOpt = variantProductRepository.findByBaseProductIdAndIdAndTenantId(productId, variantId, tenantResolverService.resolveTenant());

        if (vOpt.isPresent()) {
            VariantProduct vProd = vOpt.get();
            vProd.deleted(true);
            variantProductRepository.save(vProd);
        }
    }


    /**
     * Remove a particular product from repository
     *
     * @param baseProductRef
     * @param variantRef
     */
    @Override
    public void removeVariantByRef(String baseProductRef, String variantRef) {

        Optional<VariantProduct> vOpt = variantProductRepository.findByProductRefAndBaseProductRefAndTenantIdAndDeleted(variantRef, baseProductRef, tenantResolverService.resolveTenant(), Boolean.FALSE);

        if (vOpt.isPresent()) {
            VariantProduct vProd = vOpt.get();
            vProd.deleted(true);
            variantProductRepository.save(vProd);
        }
    }

    /**
     * Remove a variants of a product
     *
     * @param baseProductId
     */
    @Override
    public void removeVariantsByBaseProduct(String baseProductId) {
        String tenantId = tenantResolverService.resolveTenant();
        removeVariantsByBaseProductAndTenantId(baseProductId, tenantId);
    }

    /**
     * Remove a variants of a product
     *
     * @param baseProductId
     */
    @Override
    public void removeVariantsByBaseProductAndTenantId(String baseProductId, String tenantId) {
        List<VariantProduct> variants = variantProductRepository.findByBaseProductIdAndTenantIdAndDeleted(baseProductId, tenantId, Boolean.FALSE);
        List<VariantProduct> removedVariants = variants.stream().map(each -> {
            each.deleted(Boolean.TRUE);
            return each;
        }).collect(Collectors.toList());
        variantProductRepository.saveAll(removedVariants);
    }

    /**
     * Search products
     *
     * @param query
     * @return List<VariantProductDto>
     */
    @Override
    public List<VariantProductDto> searchProduct(String query) {
        return null;
    }

    public VariantProduct saveVariantProductWithReference(VariantProduct model) {
        return variantProductRepository.save(model);
    }
}
