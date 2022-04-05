package au.twc.core.product.config;

import au.twc.core.product.domain.*;
import au.twc.core.product.domain.enumeration.ProductAvailability;
import au.twc.core.product.dto.*;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrikaMapperConfiguration {

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    @Bean
    public MapperFactory orikaMapperFactory() {
        this.mapperFactory
            .classMap(ProductDto.class, Product.class)
            .fieldMap("variants", "variants")
            .aElementType(VariantProduct.class).bElementType(VariantProductDto.class).add()
            .mapNulls(false).mapNullsInReverse(false)
            .exclude("id")
            .byDefault()
            .register();
        this.mapperFactory.classMap(AttributeGroupDto.class, AttributeGroup.class)
            .mapNulls(false).mapNullsInReverse(false)
            .byDefault()
            .register();
        this.mapperFactory.classMap(AttributeValueDto.class, AttributeValue.class)
            .mapNulls(false).mapNullsInReverse(false)
            .byDefault()
            .register();

        this.mapperFactory.classMap(Product.class, ProductDto.class)
            .mapNulls(false).mapNullsInReverse(false)
            .fieldMap("variants", "variants")
            .aElementType(VariantProduct.class).bElementType(VariantProductDto.class)
            .mapNulls(false).mapNullsInReverse(false).add()
            .byDefault()
            .register();
        this.mapperFactory.classMap(AttributeGroup.class, AttributeGroupDto.class)
            .mapNulls(false).mapNullsInReverse(false)
            .byDefault()
            .register();
        this.mapperFactory.classMap(AttributeValue.class, AttributeValueDto.class)
            .mapNulls(false).mapNullsInReverse(false)
            .byDefault()
            .register();

        this.mapperFactory.classMap(VariantProduct.class, VariantProductDto.class)
            .mapNulls(false).mapNullsInReverse(false)
            .fieldMap("variantValues", "variance")
            .mapNulls(false).mapNullsInReverse(false).add()
            .fieldMap("availability", "availability")
            .aElementType(ProductAvailability.class).bElementType(ProductAvailability.class).add()
            .byDefault()
            .register();

        this.mapperFactory.classMap(VariantOptionsDto.class, VariantOptions.class)
            .mapNulls(false).mapNullsInReverse(false)
            .byDefault()
            .register();
        this.mapperFactory.classMap(VariantOptions.class, VariantOptionsDto.class)
            .mapNulls(false).mapNullsInReverse(false)
            .byDefault()
            .register();

        this.mapperFactory.classMap(VariantOptionsValueDto.class, VariantOptionsValue.class)
            .mapNulls(false).mapNullsInReverse(false)
            .byDefault()
            .register();
        this.mapperFactory.classMap(VariantOptionsValue.class, VariantOptionsValueDto.class)
            .mapNulls(false).mapNullsInReverse(false)
            .byDefault()
            .register();

        this.mapperFactory.classMap(PhysicalSpecificationDto.class, PhysicalSpecification.class)
            .mapNulls(false).mapNullsInReverse(false)
            .byDefault()
            .register();
        this.mapperFactory.classMap(PhysicalSpecification.class, PhysicalSpecificationDto.class)
            .mapNulls(false).mapNullsInReverse(false)
            .byDefault()
            .register();
        return this.mapperFactory;
    }

    @Bean
    public MapperFacade orikaMapperFacade() {
        return orikaMapperFactory().getMapperFacade();
    }
}
