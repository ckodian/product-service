package au.twc.core.product.domain;

import au.twc.core.product.dynamo.converter.VariantOptionsValueConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * A VariantProduct.
 */
@Data
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "variantproduct")
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class VariantProduct extends AbstractProduct<VariantProduct> implements Serializable {

    private static final long serialVersionUID = 1132865574586452170L;

    @DynamoDBAttribute(attributeName = "variantValues")
    @Builder.Default
    private Map<String, String> variantValues = new HashMap<>();

    @Builder.Default
    @DynamoDBAttribute(attributeName = "variant_options")
    @DynamoDBTypeConverted(converter = VariantOptionsValueConverter.class)
    private Set<VariantOptionsValue> variantOptions = new HashSet<>();

    @DynamoDBAttribute(attributeName = "baseProduct")
    @JsonIgnoreProperties(value = "variants", allowSetters = true)
    private Product baseProduct;

    @DynamoDBAttribute(attributeName = "baseProductId")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "product_baseProductId_index")
    private String baseProductId;

    @DynamoDBAttribute(attributeName = "baseProductRef")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = "product_baseProductId_index")
    private String baseProductRef;

    public VariantProduct baseProduct(Product product) {
        this.baseProduct = product;
        return this;
    }

    public VariantProduct addVariantValues(String variance, String variantValue) {
        this.variantValues.put(variance, variantValue);
        return this;
    }

    public VariantProduct removeVariantValues(String variance) {
        this.variantValues.remove(variance);
        return this;
    }

    public VariantProduct variantValues(Map<String, String> variantValues) {
        this.variantValues = variantValues;
        return this;
    }

    public VariantProduct addVariantOptions(VariantOptionsValue vValues) {
        this.variantOptions.add(vValues);
        return this;
    }

    public VariantProduct removeVariantOptions(VariantOptionsValue vValues) {
        this.variantOptions.remove(vValues);
        return this;
    }

    public VariantProduct variantOptions(Set<VariantOptionsValue> variantOptions) {
        this.variantOptions = variantOptions;
        return this;
    }

    public VariantProduct baseProductId(String baseProductId) {
        this.baseProductId = baseProductId;
        return this;
    }

    public VariantProduct baseProductRef(String baseProductRef) {
        this.baseProductRef = baseProductRef;
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    // prettier-ignore

}
