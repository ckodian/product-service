package au.twc.core.product.domain;

import au.twc.core.product.domain.enumeration.InventoryTracking;
import au.twc.core.product.dynamo.converter.VariantOptionsConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Product.
 */
@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false, onlyExplicitlyIncluded = true)
@DynamoDBTable(tableName = "product")
public class Product extends AbstractProduct<Product> implements Serializable {

    private static final long serialVersionUID = -2540459934399056273L;

    @DynamoDBAttribute(attributeName = "variants_available")
    private Boolean variantsAvailable;

    @DynamoDBAttribute(attributeName = "variants")
    @DynamoDBTypeConvertedJson
    @Builder.Default
    private Set<VariantProduct> variants = new HashSet<>();

    @DynamoDBAttribute(attributeName = "variance")
    @DynamoDBTypeConvertedJson
    @Builder.Default
    private Set<String> variance = new HashSet<>();

    @DynamoDBAttribute(attributeName = "options")
    @DynamoDBTypeConverted(converter = VariantOptionsConverter.class)
    @Builder.Default
    private Set<VariantOptions> variantOptions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Product variantsAvailable(Boolean variantsAvailable) {
        this.variantsAvailable = variantsAvailable;
        return this;
    }

    public Product variants(Set<VariantProduct> variantProducts) {
        this.variants = variantProducts;
        return this;
    }

    public Product addVariants(VariantProduct variantProduct) {
        this.variants.add(variantProduct);
        variantProduct.setBaseProduct(this);
        return this;
    }

    public Product removeVariants(VariantProduct variantProduct) {
        this.variants.remove(variantProduct);
        variantProduct.setBaseProduct(null);
        return this;
    }

    public Product addVariance(String variance) {
        this.variance.add(variance);
        return this;
    }
    public Product removeVariance(String variance) {
        this.variance.remove(variance);
        return this;
    }

    public Product variance(Set<String> variance) {
        this.variance= variance;
        return this;
    }

    public Product addVariantOptions(VariantOptions varianceOptions) {
        this.variantOptions.add(varianceOptions);
        return this;
    }
    public Product removeVariantOptions(VariantOptions varianceOptions) {
        this.variantOptions.remove(varianceOptions);
        return this;
    }

    public Product variantOptions(Set<VariantOptions> variantOptions) {
        this.variantOptions = variantOptions;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", isbn=" + getIsbn() +
            ", gtin='" + getGtin() + "'" +
            ", gtinType='" + getGtinType() + "'" +
            ", status='" + getStatus() + "'" +
            ", disabled='" + getDisabled() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", link='" + getLink() + "'" +
            ", imageLink='" + getImageLink() + "'" +
            ", additionalImageLink='" + getAdditionalImageLink() + "'" +
            ", mobileLink='" + getMobileLink() + "'" +
            ", availability='" + getAvailability() + "'" +
            ", variantsAvailable='" + getVariantsAvailable() + "'" +
            ", color='" + getColor() + "'" +
            "}";
    }
}
