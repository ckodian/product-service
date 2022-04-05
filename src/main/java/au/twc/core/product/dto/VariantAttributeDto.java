package au.twc.core.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * A VariantProductDto.
 */
public class VariantAttributeDto implements Serializable {

    private static final long serialVersionUID = -8702615519082034545L;

    private String id;

    private String attributeName;

    @JsonIgnore
    private String productId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return id;
    }

    public VariantAttributeDto id(String id) {
        this.id = id;
        return this;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public VariantAttributeDto productId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public VariantAttributeDto attributeName(String attributeName) {
        this.attributeName = attributeName;
        return this;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VariantAttributeDto)) {
            return false;
        }
        return id != null && id.equals(((VariantAttributeDto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VariantAttributeDto{" +
            "id=" + getId() +
            ", attributeName='" + getAttributeName() + "'" +
            "}";
    }
}
