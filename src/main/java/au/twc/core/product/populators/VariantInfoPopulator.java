package au.twc.core.product.populators;

import au.twc.core.product.domain.*;
import au.twc.core.product.dto.*;
import au.twc.core.product.repository.ProductRepository;
import au.twc.core.product.service.ProductService;
import au.twc.core.product.web.rest.errors.BadRequestAlertException;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.util.MapUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class VariantInfoPopulator implements Populator<VariantProductDto, VariantProduct> {

    private final VariantProductConverter variantProductConverter;

    private final ProductRepository productRepository;

    private final MapperFacade mapperFacade;

    private final ProductService productService;

    public VariantInfoPopulator(VariantProductConverter variantProductConverter,
                                ProductRepository productRepository,
                                MapperFacade mapperFacade, ProductService productService) {
        this.variantProductConverter = variantProductConverter;
        this.productRepository = productRepository;
        this.mapperFacade = mapperFacade;
        this.productService = productService;
        this.variantProductConverter.addPopulator(this);
        this.variantProductConverter.addReversePopulator(this);
    }

    @Override
    public VariantProductDto populate(VariantProductDto dto, VariantProduct model) {
        dto.id(model.getId())
            .title(model.getTitle())
            .productRef(model.getProductRef())
            .description(model.getDescription())
            .availabilityDescription(model.getAvailabilityDescription())
            .defaultVariant(model.getDefaultVariant())
            .brandId(model.getBrandId())
            .brandName(model.getBrandName())
            .minOrderQuantity(model.getMinOrderQuantity())
            .maxOrderQuantity(model.getMaxOrderQuantity())
            .calculatedPrice(model.getCalculatedPrice())
            .condition(model.getCondition())
            .cost(model.getCost())
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
            .color(model.getColor())
            .deleted(model.getDeleted())
            .digitalProduct(model.getDigitalProduct())
            .inventoryLevel(model.getInventoryLevel())
            .inventoryTracking(model.getInventoryTracking())
            .active(model.getActive())
            .featured(model.getFeatured())
            .variance(model.getVariantValues())
            .baseProductId(model.getBaseProductId())
            .baseProductRef(model.getBaseProductRef());

        if (!Objects.isNull(model.getPhysicalSpecs())) {
            PhysicalSpecificationDto phyCond = PhysicalSpecificationDto.builder()
                .maxHeight(model.getPhysicalSpecs().getMaxHeight())
                .maxWidth(model.getPhysicalSpecs().getMaxWidth())
                .maxDepth(model.getPhysicalSpecs().getMaxDepth())
                .dimensionUnitCode(model.getPhysicalSpecs().getDimensionUnitCode())
                .dimensionUnitName(model.getPhysicalSpecs().getDimensionUnitName())
                .minWeight(model.getPhysicalSpecs().getMinWeight())
                .maxWeight(model.getPhysicalSpecs().getMaxWeight())
                .weightUnitCode(model.getPhysicalSpecs().getWeightUnitCode())
                .weightUnitName(model.getPhysicalSpecs().getWeightUnitName())
                .build();
            dto.setPhysicalSpecs(phyCond);
        }

        if (!MapUtils.isEmpty(model.getVariantValues())) {
            dto.setVariance(model.getVariantValues());
        }

        if (!CollectionUtils.isEmpty(model.getVariantOptions())) {
            model.getVariantOptions().forEach(
                each -> {
                    dto.addVariantOptions(
                        VariantOptionsValueDto.builder()
                            .optionId(each.getOptionId())
                            .optionValue(each.getOptionValue())
                            .optionLabel(each.getOptionLabel())
                            .optionsImageId(each.getOptionsImageId())
                            .optionDefaultImage(each.getOptionDefaultImage()).build());
                }
            );
            dto.setVariance(model.getVariantValues());
        }

        if (!MapUtils.isEmpty(model.getAttributeGroups())) {
            model.getAttributeGroups().forEach(
                (attributeGroupkey, attributeGroupValue) -> {
                    dto.getAttributeGroups().put(attributeGroupkey, null);
                    attributeGroupValue.getAttributes().forEach(
                        (attributeKey, attributeValue) -> {
                            dto.addAttributeValue(attributeGroupkey, attributeKey, new AttributeValueDto(attributeValue.getAttribute_value(),
                                attributeValue.getValue_type()));
                        });
                });
        }

        Optional<ProductDto> prod = productService.findProductBasicInfo(model.getBaseProduct().getId());
        if (prod.isPresent()) {
            dto.setBaseProductId(prod.get().getId());
            dto.baseProduct(prod.get());
        }
        return dto;
    }

    @Override
    public VariantProduct reversePopulate(VariantProduct model, VariantProductDto dto) {

        String baseProductId = "";
        String baseProductRef = "";
        Optional<Product> prodModel = Optional.empty();

        if(StringUtils.isNotBlank(dto.getBaseProductId())) {
            prodModel = productRepository.findByIdAndTenantIdAndDeleted(dto.getBaseProductId(), model.getTenantId(), Boolean.FALSE);
            if(prodModel.isPresent()) {
                baseProductId = prodModel.get().getId();
                baseProductRef = prodModel.get().getProductRef();
            }
        } else if (prodModel.isEmpty() && StringUtils.isNotBlank(dto.getBaseProductRef())) {
            prodModel = productRepository.findByProductRefAndTenantIdAndDeleted(dto.getBaseProductRef(), model.getTenantId(), Boolean.FALSE);
            if(prodModel.isPresent()) {
                baseProductId = prodModel.get().getId();
                baseProductRef = prodModel.get().getProductRef();
            }
        } else if (prodModel.isEmpty() && StringUtils.isNotBlank(model.getBaseProductRef())) {
            prodModel = productRepository.findByProductRefAndTenantIdAndDeleted(model.getBaseProductRef(), model.getTenantId(), Boolean.FALSE);
            if(prodModel.isPresent()) {
                baseProductId = prodModel.get().getId();
                baseProductRef = prodModel.get().getProductRef();
            }
        } else if (prodModel.isEmpty() && StringUtils.isNotBlank(model.getBaseProductId())) {
            prodModel = productRepository.findByIdAndTenantIdAndDeleted(model.getBaseProductId(), model.getTenantId(), Boolean.FALSE);
            if(prodModel.isPresent()) {
                baseProductId = prodModel.get().getId();
                baseProductRef = prodModel.get().getProductRef();
            }
        }

        if (prodModel.isEmpty()) {
            throw new BadRequestAlertException("Product variant cannot be created without a valid base product", "VariantProduct", "invalidBaseProduct");
        }


        mapperFacade.map(dto, model);
        model
            .disabled(false)
            .deleted(false)
            .baseProductId(baseProductId)
            .baseProductRef(baseProductRef);

        if (!MapUtils.isEmpty(dto.getVariance())) {
            model.setVariantValues(dto.getVariance());
        }

        if (!MapUtils.isEmpty(dto.getAttributeGroups())) {
            dto.getAttributeGroups().forEach(
                (attributeGroupkey, attributeGroupValue) -> {
                    model.getAttributeGroups().put(attributeGroupkey, null);
                    attributeGroupValue.getAttributes().forEach(
                        (attributeKey, attributeValue) -> {
                            model.addAttributeValue(attributeGroupkey, attributeKey, new AttributeValue(attributeValue.getAttribute_value(),
                                attributeValue.getValue_type()));
                        });
                });
        }

        model.setBaseProduct(prodModel.get());

        if (!CollectionUtils.isEmpty(dto.getVariantOptions())) {
            dto.getVariantOptions().forEach(
                each -> {
                    model.addVariantOptions(
                        VariantOptionsValue.builder()
                            .optionId(each.getOptionId())
                            .optionValue(each.getOptionValue())
                            .optionLabel(each.getOptionLabel())
                            .optionsImageId(each.getOptionsImageId())
                            .optionDefaultImage(each.getOptionDefaultImage()).build());
                }
            );
        }

        return model;
    }

    private Optional<Product> loadProduct(String baseProductId, String baseProductRef, String gtin, String tenantId) {
        if(StringUtils.isNotBlank(baseProductId)) {
            return productRepository.findByIdAndTenantIdAndDeleted(baseProductId, tenantId, Boolean.FALSE);
        }
        if(StringUtils.isNotBlank(baseProductRef)) {
            return productRepository.findByProductRefAndTenantIdAndDeleted(baseProductRef, tenantId, Boolean.FALSE);
        }
        if(StringUtils.isNotBlank(gtin)) {
            return productRepository.findByGtinAndTenantIdAndDeleted(gtin, tenantId, Boolean.FALSE);
        }
        return Optional.empty();
    }
}
