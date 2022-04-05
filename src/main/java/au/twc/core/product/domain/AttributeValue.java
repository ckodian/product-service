package au.twc.core.product.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDBDocument
@EqualsAndHashCode(callSuper=false, onlyExplicitlyIncluded = true)
public class AttributeValue implements Serializable {

    @DynamoDBAttribute(attributeName = "attribute_value")
    private String attribute_value;

    @DynamoDBAttribute(attributeName = "value_type")
    @DynamoDBTypeConvertedEnum
    private AttributeValueType value_type;
}
