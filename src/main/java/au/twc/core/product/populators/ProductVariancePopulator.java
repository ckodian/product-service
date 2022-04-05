package au.twc.core.product.populators;

import au.twc.core.product.domain.Product;
import au.twc.core.product.domain.VariantOptions;
import au.twc.core.product.dto.ProductDto;
import au.twc.core.product.dto.VariantOptionsDto;
import au.twc.core.product.dto.VariantProductDto;
import au.twc.core.product.service.VariantProductService;
import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Set;

@Component
public class ProductVariancePopulator implements Populator<ProductDto, Product> {

    private static final Logger log = LoggerFactory.getLogger(ProductVariancePopulator.class);

    private final ProductConverter productConverter;

    private final VariantProductService variantProductService;

    public ProductVariancePopulator(ProductConverter productConverter,
                                    VariantProductService variantProductService) {
        this.productConverter = productConverter;
        this.variantProductService = variantProductService;
        this.productConverter.addPopulator(this);
        this.productConverter.addReversePopulator(this);
    }

    @Override
    public ProductDto populate(ProductDto dto, Product model) {
        dto.variantsAvailable(model.getVariantsAvailable());
        populateVariantAttribute(dto, model);
        populateVariantProduct(dto, model);
        return dto;
    }

    @Override
    public Product reversePopulate(Product model, ProductDto dto) {
        Boolean variance_available = Boolean.FALSE;
        Boolean variance_opt_available = Boolean.FALSE;



        variance_available = BooleanUtils.isFalse(CollectionUtils.isEmpty(model.getVariance()) && CollectionUtils.isEmpty(dto.getVariance()));
        variance_opt_available = BooleanUtils.isFalse(CollectionUtils.isEmpty(model.getVariantOptions())
            && CollectionUtils.isEmpty(dto.getVariantOptions()));
        model.variantsAvailable(variance_available || variance_opt_available);
        reversePopulateVariantAttribute(dto, model);
        return model;
    }

    private void populateVariantProduct(ProductDto dto, Product model) {

        Set<VariantProductDto> vProds = variantProductService.findVariantProductBasicInfoByBaseProduct(model.getId());
        dto.setVariants(vProds);
    }

    private void populateVariantAttribute(ProductDto dto, Product model) {

        if (log.isDebugEnabled()) {
            log.debug("Logging product variance here {}", model.getVariance());
        }

        if(!CollectionUtils.isEmpty(model.getVariance())) {
            dto.setVariance(model.getVariance());
        }

        if(!CollectionUtils.isEmpty(model.getVariantOptions())) {
            model.getVariantOptions().stream().
                forEach(
                    each -> {
                        VariantOptionsDto vo = VariantOptionsDto.builder()
                            .optionId(each.getOptionId())
                            .optionName(each.getOptionName()).build();
                        dto.addVariantOptions(vo);
                    }
                );
        }
    }

    private void reversePopulateVariantAttribute(ProductDto dto, Product model) {

        if (log.isDebugEnabled()) {
            log.debug("Logging product variance here {}", dto.getVariance());
        }

        if(!CollectionUtils.isEmpty(dto.getVariance())) {
            dto.getVariance().stream().
                forEach(each -> model.addVariance(each));
        }

        if(!CollectionUtils.isEmpty(dto.getVariantOptions())) {
            dto.getVariantOptions().stream().
                forEach(
                    each -> {
                        VariantOptions vo = VariantOptions.builder()
                            .optionId(each.getOptionId())
                            .optionName(each.getOptionName()).build();
                        model.addVariantOptions(vo);
                    }
                );
        }
    }
}
