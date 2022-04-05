package au.twc.core.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhysicalSpecificationDto {

    private Double maxHeight;

    private Double maxWidth;

    private Double maxDepth;

    private String dimensionUnitCode;

    private String dimensionUnitName;

    private Double minWeight;

    private Double maxWeight;

    private String weightUnitCode;

    private String weightUnitName;

    public PhysicalSpecificationDto maxHeight(Double maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public PhysicalSpecificationDto maxWidth(Double maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public PhysicalSpecificationDto maxDepth(Double maxDepth) {
        this.maxDepth = maxDepth;
        return this;
    }

    public PhysicalSpecificationDto dimensionUnitCode(String dimensionUnitCode) {
        this.dimensionUnitCode = dimensionUnitCode;
        return this;
    }

    public PhysicalSpecificationDto dimensionUnitName(String dimensionUnitName) {
        this.dimensionUnitName = dimensionUnitName;
        return this;
    }

    public PhysicalSpecificationDto minWeight(Double minWeight) {
        this.minWeight = minWeight;
        return this;
    }

    public PhysicalSpecificationDto maxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
        return this;
    }

    public PhysicalSpecificationDto weightUnitCode(String weightUnitCode) {
        this.weightUnitCode = weightUnitCode;
        return this;
    }

    public PhysicalSpecificationDto weightUnitName(String weightUnitName) {
        this.weightUnitName = weightUnitName;
        return this;
    }


}
