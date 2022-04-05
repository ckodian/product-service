package au.twc.core.product.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@DynamoDBDocument
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhysicalSpecification implements Serializable {

    private static final long serialVersionUID = 8086492071342344542L;

    @DynamoDBAttribute(attributeName = "max_height")
    private Double maxHeight;

    @DynamoDBAttribute(attributeName = "max_width")
    private Double maxWidth;

    @DynamoDBAttribute(attributeName = "max_depth")
    private Double maxDepth;

    @DynamoDBAttribute(attributeName = "dimension_unit_code")
    private String dimensionUnitCode;

    @DynamoDBAttribute(attributeName = "dimension_unit_name")
    private String dimensionUnitName;

    @DynamoDBAttribute(attributeName = "min_weight")
    private Double minWeight;

    @DynamoDBAttribute(attributeName = "max_weight")
    private Double maxWeight;

    @DynamoDBAttribute(attributeName = "weight_unit_code")
    private String weightUnitCode;

    @DynamoDBAttribute(attributeName = "weight_unit_name")
    private String weightUnitName;


}
