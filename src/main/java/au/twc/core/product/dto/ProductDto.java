package au.twc.core.product.dto;

import au.twc.core.product.domain.enumeration.*;
import lombok.*;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * A ProductDto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ProductDto implements Serializable {

    private static final long serialVersionUID = 7438024827126917327L;

    @EqualsAndHashCode.Include(rank = 1000)
    private String id;

    private String title;

    @EqualsAndHashCode.Include(rank = 900)
    private String productRef;

    private String description;

    private String availabilityDescription;

    private String defaultVariant;

    private String brandId;

    private String brandName;

    private Integer minOrderQuantity;

    private Integer maxOrderQuantity;

    private BigDecimal calculatedPrice;

    private ProductCondition condition;

    private BigDecimal cost;

    @EqualsAndHashCode.Include(rank = 500)
    private String gtin;

    private GtinType gtinType;

    @Pattern(regexp = "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$")
    private String isbn;

    private PhysicalSpecificationDto physicalSpecs;

    private ProductStatus status;

    private Boolean disabled;

    private Date expirationDate;

    private String link;

    private String imageLink;

    private String additionalImageLink;

    private String mobileLink;

    private ProductAvailability availability;

    private String color;

    @Builder.Default
    private Boolean deleted = Boolean.FALSE;

    private Boolean digitalProduct;

    private Integer inventoryLevel;

    @Builder.Default
    private InventoryTracking inventoryTracking = InventoryTracking.none;

    private Boolean active;

    private Boolean featured;

    private Boolean variantsAvailable;

    @Builder.Default
    private Set<VariantProductDto> variants = new HashSet<>();

    @Builder.Default
    private Set<String> variance = new HashSet<>();

    @Builder.Default
    private Set<VariantOptionsDto> variantOptions = new HashSet<>();

    @Builder.Default
    private Map<String, AttributeGroupDto> attributeGroups = new HashMap<>();

    public ProductDto addAttributeGroup(String key, AttributeGroupDto group) {
        this.attributeGroups.put(key, group);
        return this;
    }

    public ProductDto addAttributeValue(@NonNull String groupKey, @NonNull String attributeName, @NonNull AttributeValueDto value) {

        AttributeGroupDto group = this.attributeGroups.get(groupKey);

        if (group == null) {
            group = AttributeGroupDto.builder().build();
        }
        group.getAttributes().put(attributeName, value);
        this.addAttributeGroup(groupKey, group);

        return this;
    }

    public ProductDto removeAttributeValue(@NonNull String groupKey, @NonNull String attributeName) {

        AttributeGroupDto group = this.attributeGroups.get(groupKey);
        if (group != null) {
            group.getAttributes().remove(attributeName);
        }
        return this;
    }

    public ProductDto removeAttributeGroup(String key, AttributeGroupDto group) {
        this.attributeGroups.remove(key, group);
        return this;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public ProductDto id(String id) {
        this.id = id;
        return this;
    }

    public ProductDto title(String title) {
        this.title = title;
        return this;
    }

    public ProductDto productRef(String productRef) {
        this.productRef = productRef;
        return this;
    }

    public ProductDto description(String description) {
        this.description = description;
        return this;
    }

    public ProductDto availabilityDescription(String availabilityDescription) {
        this.availabilityDescription = availabilityDescription;
        return this;
    }

    public ProductDto defaultVariant(String defaultVariant) {
        this.defaultVariant = defaultVariant;
        return this;
    }

    public ProductDto brandId(String brandId) {
        this.brandId = brandId;
        return this;
    }

    public ProductDto brandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public ProductDto minOrderQuantity(Integer minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
        return this;
    }

    public ProductDto maxOrderQuantity(Integer maxOrderQuantity) {
        this.maxOrderQuantity = maxOrderQuantity;
        return this;
    }

    public ProductDto calculatedPrice(BigDecimal calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
        return this;
    }

    public ProductDto condition(ProductCondition condition) {
        this.condition = condition;
        return this;
    }

    public ProductDto cost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    public ProductDto gtin(String gtin) {
        this.gtin = gtin;
        return this;
    }

    public ProductDto gtinType(GtinType gtinType) {
        this.gtinType = gtinType;
        return this;
    }

    public ProductDto isbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public ProductDto physicalSpecs(PhysicalSpecificationDto physicalSpecs) {
        this.physicalSpecs = physicalSpecs;
        return this;
    }

    public ProductDto status(ProductStatus status) {
        this.status = status;
        return this;
    }

    public ProductDto disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public ProductDto expirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public ProductDto link(String link) {
        this.link = link;
        return this;
    }

    public ProductDto imageLink(String imageLink) {
        this.imageLink = imageLink;
        return this;
    }

    public ProductDto additionalImageLink(String additionalImageLink) {
        this.additionalImageLink = additionalImageLink;
        return this;
    }

    public ProductDto mobileLink(String mobileLink) {
        this.mobileLink = mobileLink;
        return this;
    }

    public ProductDto availability(ProductAvailability availability) {
        this.availability = availability;
        return this;
    }

    public ProductDto color(String color) {
        this.color = color;
        return this;
    }

    public ProductDto deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public ProductDto digitalProduct(Boolean digitalProduct) {
        this.digitalProduct = digitalProduct;
        return this;
    }

    public ProductDto inventoryLevel(Integer inventoryLevel) {
        this.inventoryLevel = inventoryLevel;
        return this;
    }

    public ProductDto inventoryTracking(InventoryTracking inventoryTracking) {
        this.inventoryTracking = inventoryTracking;
        return this;
    }

    public ProductDto active(Boolean active) {
        this.active = active;
        return this;
    }

    public ProductDto featured(Boolean featured) {
        this.featured = featured;
        return this;
    }

    public ProductDto variantsAvailable(Boolean variantsAvailable) {
        this.variantsAvailable = variantsAvailable;
        return this;
    }

    public ProductDto variants(Set<VariantProductDto> variants) {
        this.variants = variants;
        return this;
    }

    public ProductDto variance(Set<String> variance) {
        this.variance = variance;
        return this;
    }

    public ProductDto addVariantOptions(VariantOptionsDto varianceOptions) {
        this.variantOptions.add(varianceOptions);
        return this;
    }
    public ProductDto removeVariantOptions(VariantOptionsDto varianceOptions) {
        this.variantOptions.remove(varianceOptions);
        return this;
    }

    public ProductDto variantOptions(Set<VariantOptionsDto> variantOptions) {
        this.variantOptions = variantOptions;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
}
