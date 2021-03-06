package au.twc.core.product.domain;

import au.twc.core.product.domain.enumeration.*;
import au.twc.core.product.dynamo.converter.AttributeGroupsConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@DynamoDBDocument
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AbstractProduct<T extends AbstractProduct> extends AbstractEntity implements Serializable {

    @DynamoDBAutoGeneratedKey
    @DynamoDBHashKey
    @EqualsAndHashCode.Include(rank = 1000)
    private String id;

    @NotNull
    @DynamoDBAttribute(attributeName = "tenant_id")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "product_tenantId_index")
    @EqualsAndHashCode.Include(rank = 1000)
    private String tenantId;

    @NotNull
    @DynamoDBAttribute(attributeName = "title")
    private String title;

    @NotNull
    @DynamoDBAttribute(attributeName = "productRef")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "product_productRef_index")
    @EqualsAndHashCode.Include(rank = 900)
    private String productRef;

    @DynamoDBAttribute(attributeName = "description")
    private String description;

    @DynamoDBAttribute(attributeName = "availability_description")
    @Max(value = 255)
    private String availabilityDescription;

    @DynamoDBAttribute(attributeName = "default_variant")
    private String defaultVariant;

    @DynamoDBAttribute(attributeName = "brand_id")
    private String brandId;

    @DynamoDBAttribute(attributeName = "brand_name")
    @Max(value = 255)
    private String brandName;

    @DynamoDBAttribute(attributeName = "min_order_quantity")
    private Integer minOrderQuantity;

    @DynamoDBAttribute(attributeName = "max_order_quantity")
    private Integer maxOrderQuantity;

    @DynamoDBAttribute(attributeName = "calculated_price")
    @DynamoDBTypeConvertedJson
    private BigDecimal calculatedPrice;

    @DynamoDBAttribute(attributeName = "condition")
    @DynamoDBTypeConvertedJson
    @Builder.Default
    private ProductCondition condition = ProductCondition.New;

    @DynamoDBAttribute(attributeName = "cost")
    @DynamoDBTypeConvertedJson
    private BigDecimal cost;

    @DynamoDBAttribute(attributeName = "gtin")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "product_gtin_index")
    @EqualsAndHashCode.Include(rank = 500)
    private String gtin;

    @DynamoDBAttribute(attributeName = "gtin_type")
    @DynamoDBTypeConvertedEnum
    private GtinType gtinType;

    @DynamoDBAttribute(attributeName = "isbn")
    @Pattern(regexp = "^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "product_isbn_index")
    private String isbn;

    @DynamoDBAttribute(attributeName = "physical_specs")
    @DynamoDBTypeConvertedJson
    private PhysicalSpecification physicalSpecs;

    @DynamoDBAttribute(attributeName = "status")
    @DynamoDBTypeConvertedEnum
    private ProductStatus status;

    @DynamoDBAttribute(attributeName = "disabled")
    private Boolean disabled;

    @DynamoDBAttribute(attributeName = "expiration_date")
    @DynamoDBTypeConvertedTimestamp
    private Date expirationDate;

    @DynamoDBAttribute(attributeName = "link")
    private String link;

    @DynamoDBAttribute(attributeName = "image_link")
    private String imageLink;

    @DynamoDBAttribute(attributeName = "additional_image_link")
    private String additionalImageLink;

    @DynamoDBAttribute(attributeName = "mobile_link")
    private String mobileLink;

    @DynamoDBAttribute(attributeName = "availability")
    @DynamoDBTypeConvertedEnum
    @Builder.Default
    private ProductAvailability availability = ProductAvailability.available;

    @DynamoDBAttribute(attributeName = "color")
    private String color;

    @DynamoDBAttribute(attributeName = "deleted")
    @Builder.Default
    private Boolean deleted = Boolean.FALSE;

    @DynamoDBAttribute(attributeName = "digital_product")
    @Builder.Default
    private Boolean digitalProduct = Boolean.FALSE;

    @DynamoDBAttribute(attributeName = "inventory_level")
    private Integer inventoryLevel;

    @DynamoDBAttribute(attributeName = "inventory_tracking")
    @DynamoDBTypeConvertedEnum
    private InventoryTracking inventoryTracking;

    @DynamoDBAttribute(attributeName = "active")
    private Boolean active;

    @DynamoDBAttribute(attributeName = "featured")
    private Boolean featured;

    @Builder.Default
    @DynamoDBAttribute(attributeName = "attribute_group")
    @DynamoDBTypeConverted(converter = AttributeGroupsConverter.class)
    private Map<String, AttributeGroup> attributeGroups = new HashMap<>();

    public T addAttributeGroup(String key, AttributeGroup group) {
        this.attributeGroups.put(key, group);
        return (T) this;
    }
    public T addAttributeValue(@NonNull String groupKey, @NonNull String attributeName, @NonNull AttributeValue value) {

        AttributeGroup group = this.attributeGroups.get(groupKey);

        if (group == null) {
            group = AttributeGroup.builder().build();
            group.getAttributes().put(attributeName, value);
            this.addAttributeGroup(groupKey, group);
			return (T) this;
        }
        group.getAttributes().put(attributeName, value);

        return (T) this;
    }

    public T removeAttributeValue(@NonNull String groupKey, @NonNull String attributeName) {

        AttributeGroup group = this.attributeGroups.get(groupKey);
        if (group != null) {
            group.getAttributes().remove(attributeName);
        }
        return (T) this;
    }

    public T removeAttributeGroup(String key, AttributeGroup group) {
        this.attributeGroups.remove(key, group);
        return (T) this;
    }

    public T id(String id) {
        this.id = id;
        return (T) this;
    }

    public T title(String title) {
        this.title = title;
        return (T) this;
    }

    public T productRef(String productRef) {
        this.productRef = productRef;
        return (T) this;
    }

    public T description(String description) {
        this.description = description;
        return (T) this;
    }

    public T availabilityDescription(String availabilityDescription) {
        this.availabilityDescription = availabilityDescription;
        return (T) this;
    }

    public T defaultVariant(String defaultVariant) {
        this.defaultVariant = defaultVariant;
        return (T) this;
    }

    public T brandName(String brandName) {
        this.brandName = brandName;
        return (T) this;
    }

    public T brandId(String brandId) {
        this.brandId = brandId;
        return (T) this;
    }

    public T minOrderQuantity(Integer minOrderQuantity) {
        this.minOrderQuantity = minOrderQuantity;
        return (T) this;
    }

    public T maxOrderQuantity(Integer maxOrderQuantity) {
        this.maxOrderQuantity = maxOrderQuantity;
        return (T) this;
    }

    public T condition(ProductCondition condition) {
        this.condition = condition;
        return (T) this;
    }

    public T cost(BigDecimal cost) {
        this.cost = cost;
        return (T) this;
    }

    public T status(ProductStatus status) {
        this.status = status;
        return (T) this;
    }

    public T disabled(Boolean disabled) {
        this.disabled = disabled;
        return (T) this;
    }

    public T expirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        return (T) this;
    }

    public T link(String link) {
        this.link = link;
        return (T) this;
    }

    public T imageLink(String imageLink) {
        this.imageLink = imageLink;
        return (T) this;
    }

    public T additionalImageLink(String additionalImageLink) {
        this.additionalImageLink = additionalImageLink;
        return (T) this;
    }

    public T mobileLink(String mobileLink) {
        this.mobileLink = mobileLink;
        return (T) this;
    }

    public T calculatedPrice(BigDecimal calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
        return (T) this;
    }

    public T color(String color) {
        this.color = color;
        return (T) this;
    }

    public T deleted(Boolean deleted) {
        this.deleted = deleted;
        return (T) this;
    }

    public T digitalProduct(Boolean digitalProduct) {
        this.digitalProduct = digitalProduct;
        return (T) this;
    }

    public T inventoryLevel(Integer inventoryLevel) {
        this.inventoryLevel = inventoryLevel;
        return (T) this;
    }

    public T inventoryTracking(InventoryTracking inventoryTracking) {
        this.inventoryTracking = inventoryTracking;
        return (T) this;
    }

    public T active(Boolean active) {
        this.active = deleted;
        return (T) this;
    }

    public T featured(Boolean featured) {
        this.featured = featured;
        return (T) this;
    }

    public T gtin(String gtin) {
        this.gtin = gtin;
        return (T) this;
    }

    public T gtinType(GtinType gtinType) {
        this.gtinType = gtinType;
        return (T) this;
    }

    public T isbn(String isbn) {
        this.isbn = isbn;
        return (T) this;
    }

    public T availability(ProductAvailability productAvailability) {
        this.availability = availability;
        return (T) this;
    }
}
