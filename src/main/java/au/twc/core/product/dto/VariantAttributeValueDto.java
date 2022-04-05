package au.twc.core.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * A VariantAttributeValueDto.
 */
public class VariantAttributeValueDto implements Serializable {

    private static final long serialVersionUID = 481403734613888255L;

    private String id;

    private String attributeValue;

    private String attributeName;

    @JsonIgnore
    private String baseProductId;

    @JsonIgnore
    private VariantProductDto variantProduct;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public VariantAttributeValueDto id(String id) {
        this.id = id;
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public VariantAttributeValueDto attributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
        return this;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public VariantAttributeValueDto attributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public VariantProductDto getVariantProduct() {
        return variantProduct;
    }

    public VariantAttributeValueDto variantProduct(VariantProductDto variantProduct) {
        this.variantProduct = variantProduct;
        return this;
    }
    public void setVariantProduct(VariantProductDto variantProduct) {
        this.variantProduct = variantProduct;
    }

    public String getBaseProductId() {
        return baseProductId;
    }

    public VariantAttributeValueDto baseProductId(String baseProductId) {
        this.baseProductId = baseProductId;
        return this;
    }

    public void setBaseProductId(String baseProductId) {
        this.baseProductId = baseProductId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VariantAttributeValueDto)) {
            return false;
        }
        return id != null && id.equals(((VariantAttributeValueDto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VariantAttributeValueDto{" +
            "id=" + getId() +
            ", attributeName='" + getAttributeName() + "'" +
            ", attributeValue='" + getAttributeValue() + "'" +
            "}";
    }
}
