package au.twc.core.product.populators;

import au.twc.core.product.domain.PhysicalSpecification;
import au.twc.core.product.domain.Product;
import au.twc.core.product.domain.VariantProduct;
import au.twc.core.product.dto.PhysicalSpecificationDto;
import au.twc.core.product.dto.ProductDto;
import au.twc.core.product.dto.VariantProductDto;
import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;

@Component
public class ProductInfoPopulator implements Populator<ProductDto, Product> {

    private static final Logger log = LoggerFactory.getLogger(ProductInfoPopulator.class);

    private ProductConverter productConverter;

    private final MapperFacade mapperFacade;

    public ProductInfoPopulator(ProductConverter productConverter, MapperFacade mapperFacade) {
        this.productConverter = productConverter;
        this.mapperFacade = mapperFacade;
        this.productConverter.addPopulator(this);
        this.productConverter.addReversePopulator(this);
    }

    @Override
    public ProductDto populate(ProductDto dto, Product model) {
        Set<VariantProduct> variants = model.getVariants();
        model.setVariants(null);
        dto.setVariants(null);
        mapperFacade.map(model, dto);
        dto.variantsAvailable(BooleanUtils.isTrue(dto.getVariantsAvailable()) ? true : false);
        return dto;
    }

    @Override
    public Product reversePopulate(Product model, ProductDto dto) {
        mapperFacade.map(dto, model);
        model.variantsAvailable(BooleanUtils.isTrue(dto.getVariantsAvailable()) ? true : false);
        return model;
    }
}
