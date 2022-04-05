package au.twc.core.product.dto;

import au.twc.core.product.domain.enumeration.*;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * A VariantProductDto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class VariantProductDto implements Serializable {

    private static final long serialVersionUID = -2050019788652493099L;

    @EqualsAndHashCode.Include
    private String id;

    private String title;

    @EqualsAndHashCode.Include
    private String productRef;

    private String description;

    @Max(value = 255)
    private String availabilityDescription;

    private String defaultVariant;

    private String brandId;

    @Max(value = 255)
    private String brandName;

    private Integer minOrderQuantity;

    private Integer maxOrderQuantity;

    private BigDecimal calculatedPrice;

    private ProductCondition condition;

    private BigDecimal cost;

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

    private InventoryTracking inventoryTracking;

    private Boolean active;

    private Boolean featured;

    private Map<String, String> variance;

    private ProductDto baseProduct;

    private String baseProductId;

    private String baseProductRef;

    @Builder.Default
    private Set<VariantOptionsValueDto> variantOptions = new HashSet<>();

    @Builder.Default
    private Map<String, AttributeGroupDto> attributeGroups = new HashMap<>();

    public VariantProductDto addAttributeGroup(String key, AttributeGroupDto group) {
        this.attributeGroups.put(key, group);
        return this;
    }

    public VariantProductDto addAttributeValue(@NonNull String groupKey, @NonNull String attributeName, @NonNull AttributeValueDto value) {

        AttributeGroupDto group = this.attributeGroups.get(groupKey);

        if (group == null) {
            group = AttributeGroupDto.builder().build();
        }
        group.getAttributes().put(attributeName, value);
        this.addAttributeGroup(groupKey, group);
        return this;
    }

    public VariantProductDto removeAttributeValue(@NonNull String groupKey, @NonNull String attributeName) {

        AttributeGroupDto group = this.attributeGroups.get(groupKey);
        if (group != null) {
            group.getAttributes().remove(attributeName);
        }
        return this;
    }

    public VariantProductDto removeAttributeGroup(String key, AttributeGroupDto group) {
        this.attributeGroups.remove(key, group);
        return this;
    }

    public VariantProductDto id(String id) {
        this.id = id;
        return this;
    }

    public VariantProductDto title(String title) {
        this.title = title;
        return this;
    }

    public VariantProductDto productRef(String productRef) {
        this.productRef = productRef;
        return this;
    }

    public VariantProductDto description(String description) {
        this.description = description;
        return this;
    }

    public VariantProductDto availabilityDescription(String availabilityDescription) {
        this.availabilityDescription = availabilityDescription;
        return this;
    }

    public VariantProductDto defaultVariant(String defaultVariant) {
        this.defaultVariant = defaultVariant;
        return this;
    }

    public VariantProductDto brandId(String brandId) {
        this.brandId = brandId;
        return this;
    }

    public VariantProductDto brandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public VariantProductDto minOrderQuantity(Integer minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
        return this;
    }

    public VariantProductDto maxOrderQuantity(Integer maxOrderQuantity) {
        this.maxOrderQuantity = maxOrderQuantity;
        return this;
    }

    public VariantProductDto calculatedPrice(BigDecimal calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
        return this;
    }

    public VariantProductDto condition(ProductCondition condition) {
        this.condition = condition;
        return this;
    }

    public VariantProductDto cost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    public VariantProductDto gtin(String gtin) {
        this.gtin = gtin;
        return this;
    }

    public VariantProductDto gtinType(GtinType gtinType) {
        this.gtinType = gtinType;
        return this;
    }

    public VariantProductDto isbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public VariantProductDto physicalSpecs(PhysicalSpecificationDto physicalSpecs) {
        this.physicalSpecs = physicalSpecs;
        return this;
    }

    public VariantProductDto status(ProductStatus status) {
        this.status = status;
        return this;
    }

    public VariantProductDto disabled(Boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public VariantProductDto expirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public VariantProductDto link(String link) {
        this.link = link;
        return this;
    }

    public VariantProductDto imageLink(String imageLink) {
        this.imageLink = imageLink;
        return this;
    }

    public VariantProductDto additionalImageLink(String additionalImageLink) {
        this.additionalImageLink = additionalImageLink;
        return this;
    }

    public VariantProductDto mobileLink(String mobileLink) {
        this.mobileLink = mobileLink;
        return this;
    }

    public VariantProductDto availability(ProductAvailability availability) {
        this.availability = availability;
        return this;
    }

    public VariantProductDto color(String color) {
        this.color = color;
        return this;
    }

    public VariantProductDto deleted(Boolean deleted) {
        this.deleted = deleted;
        return this;
    }

    public VariantProductDto digitalProduct(Boolean digitalProduct) {
        this.digitalProduct = digitalProduct;
        return this;
    }

    public VariantProductDto inventoryLevel(Integer inventoryLevel) {
        this.inventoryLevel = inventoryLevel;
        return this;
    }

    public VariantProductDto inventoryTracking(InventoryTracking inventoryTracking) {
        this.inventoryTracking = inventoryTracking;
        return this;
    }

    public VariantProductDto active(Boolean active) {
        this.active = active;
        return this;
    }

    public VariantProductDto featured(Boolean featured) {
        this.featured = featured;
        return this;
    }

    public VariantProductDto variance(Map<String, String> variance) {
        this.variance = variance;
        return this;
    }

    public VariantProductDto baseProduct(ProductDto baseProduct) {
        this.baseProduct = baseProduct;
        return this;
    }

    public VariantProductDto baseProductId(String baseProductId) {
        this.baseProductId = baseProductId;
        return this;
    }

    public VariantProductDto baseProductRef(String baseProductRef) {
        this.baseProductRef = baseProductRef;
        return this;
    }

    public VariantProductDto variantOptions(Set<VariantOptionsValueDto> variantOptions) {
        this.variantOptions = variantOptions;
        return this;
    }

    public VariantProductDto addVariantOptions(VariantOptionsValueDto varianceOptionValue) {
        this.variantOptions.add(varianceOptionValue);
        return this;
    }
    public VariantProductDto removeVariantOptions(VariantOptionsValueDto varianceOptionValue) {
        this.variantOptions.remove(varianceOptionValue);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    // prettier-ignore

    @Override
    public String toString() {
        return "VariantProductDto{" +
            "id='" + id + '\'' +
            ", title='" + title + '\'' +
            ", productRef='" + productRef + '\'' +
            ", description='" + description + '\'' +
            ", availabilityDescription='" + availabilityDescription + '\'' +
            ", defaultVariant='" + defaultVariant + '\'' +
            ", brandId='" + brandId + '\'' +
            ", brandName='" + brandName + '\'' +
            ", minOrderQuantity=" + minOrderQuantity +
            ", maxOrderQuantity=" + maxOrderQuantity +
            ", calculatedPrice=" + calculatedPrice +
            ", condition=" + condition +
            ", cost=" + cost +
            ", gtin='" + gtin + '\'' +
            ", gtinType=" + gtinType +
            ", isbn='" + isbn + '\'' +
            ", physicalSpecs=" + physicalSpecs +
            ", status=" + status +
            ", disabled=" + disabled +
            ", expirationDate=" + expirationDate +
            ", link='" + link + '\'' +
            ", imageLink='" + imageLink + '\'' +
            ", additionalImageLink='" + additionalImageLink + '\'' +
            ", mobileLink='" + mobileLink + '\'' +
            ", availability=" + availability +
            ", color='" + color + '\'' +
            ", deleted=" + deleted +
            ", digitalProduct=" + digitalProduct +
            ", inventoryLevel=" + inventoryLevel +
            ", inventoryTracking=" + inventoryTracking +
            ", active=" + active +
            ", featured=" + featured +
            ", variance=" + variance +
            ", baseProduct=" + baseProduct +
            ", baseProductId='" + baseProductId + '\'' +
            '}';
    }

}
