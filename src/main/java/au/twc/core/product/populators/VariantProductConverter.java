package au.twc.core.product.populators;

import au.twc.core.product.domain.VariantProduct;
import au.twc.core.product.dto.VariantProductDto;
import au.twc.core.product.repository.ProductRepository;
import au.twc.core.product.repository.VariantProductRepository;
import au.twc.core.product.service.impl.TenantResolverService;
import au.twc.core.product.web.rest.errors.BadRequestAlertException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.MapUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class VariantProductConverter extends AbstractConverter<VariantProductDto, VariantProduct> {

    private final TenantResolverService tenantResolverService;

    private VariantProductRepository variantProductRepository;

    private ProductRepository productRepository;

    private AmazonDynamoDB amazonDynamoDB;

    private static final Logger log = LoggerFactory.getLogger(VariantProductConverter.class);

    public VariantProductConverter(TenantResolverService tenantResolverService, VariantProductRepository variantProductRepository,
                                   ProductRepository productRepository,
                                   AmazonDynamoDB amazonDynamoDB) {
        this.tenantResolverService = tenantResolverService;
        this.variantProductRepository = variantProductRepository;
        this.productRepository = productRepository;
        this.amazonDynamoDB = amazonDynamoDB;
    }

    @Override
    public VariantProductDto getDtoItem(VariantProduct dto) {
        return new VariantProductDto();
    }

    @Override
    public VariantProduct getModelItem(VariantProductDto dto) {
        String tenant = tenantResolverService.resolveTenant();
        Optional<VariantProduct> vProd = Optional.empty();

        if (StringUtils.isNotBlank(dto.getId())) {
            log.debug("Finding Variant Product by ID [{}]", dto.getId());
            vProd = variantProductRepository.findByIdAndTenantIdAndDeleted(dto.getId(), tenant, Boolean.FALSE);
        }

        if (vProd.isEmpty() && StringUtils.isNotBlank(dto.getProductRef())) {
            log.debug("Finding Variant Product by ID [{}]", dto.getId());
            vProd = variantProductRepository.findByProductRefAndTenantIdAndDeleted(dto.getProductRef(), tenant, Boolean.FALSE);
        }

        return vProd.isPresent() ? vProd.get() : VariantProduct.builder().deleted(Boolean.FALSE).tenantId(tenant).build();
    }

    public List<VariantProduct> lookupVariantProduct(VariantProductDto dto, String tenant) {

        DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        DynamoDBQueryExpression<VariantProduct> queryExpression = new DynamoDBQueryExpression<>();
        Map<String, AttributeValue> eav = new HashMap<>();
        PaginatedQueryList<VariantProduct> vProds = null;
        eav.put(":tenantId", new AttributeValue().withS(tenant));
        queryExpression.withKeyConditionExpression("tenantId = :tenantId")
            .withIndexName("product_tenantId_index")
            .withConsistentRead(false);
        if (!StringUtils.isEmpty(dto.getProductRef())) {
            eav.put(":productRef", new AttributeValue().withS(dto.getProductRef()));
            queryExpression
                .withKeyConditionExpression("productRef = :productRef")
                .withIndexName("product_productRef_index")
                .withConsistentRead(false)
                .withExpressionAttributeValues(eav);
            return dynamoDBMapper.query(VariantProduct.class, queryExpression);
        } else if (!StringUtils.isEmpty(dto.getGtin())) {
            eav.put(":gtin", new AttributeValue().withS(dto.getGtin()));
            queryExpression
                .withKeyConditionExpression("gtin = :gtin")
                .withIndexName("product_gtin_index")
                .withConsistentRead(false)
                .withExpressionAttributeValues(eav);
            return dynamoDBMapper.query(VariantProduct.class, queryExpression);
        }

        return Collections.emptyList();
    }
}
