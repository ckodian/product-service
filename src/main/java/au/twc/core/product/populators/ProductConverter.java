package au.twc.core.product.populators;

import au.twc.core.product.domain.Product;
import au.twc.core.product.dto.ProductDto;
import au.twc.core.product.repository.ProductRepository;
import au.twc.core.product.service.impl.TenantResolverService;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ProductConverter extends AbstractConverter<ProductDto, Product> {

    private final Logger log = LoggerFactory.getLogger(ProductConverter.class);

    private final TenantResolverService tenantResolverService;

    private final AmazonDynamoDB amazonDynamoDB;

    private final ProductRepository productRepository;

    public ProductConverter(TenantResolverService tenantResolverService,
                            AmazonDynamoDB amazonDynamoDB,
                            ProductRepository productRepository) {
        this.tenantResolverService = tenantResolverService;
        this.amazonDynamoDB = amazonDynamoDB;
        this.productRepository = productRepository;
    }

    @Override
    public Product getModelItem(ProductDto dto) {
        String tenant = tenantResolverService.resolveTenant();
        Optional<Product> product = Optional.empty();

        if (!StringUtils.isEmpty(dto.getId())) {
            product = productRepository.findByIdAndTenantIdAndDeleted(dto.getId(), tenant, Boolean.FALSE);
        }

        if (product.isEmpty()
            && !StringUtils.isEmpty(dto.getProductRef())) {
            product = productRepository.findByProductRefAndTenantIdAndDeleted(dto.getProductRef(), tenant, Boolean.FALSE);
        }

        return product.isPresent() ? product.get() : Product.builder().tenantId(tenant).build();
    }

    @Override
    public ProductDto getDtoItem(Product model) {
        return new ProductDto();
    }


}
