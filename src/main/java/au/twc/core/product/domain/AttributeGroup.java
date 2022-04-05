package au.twc.core.product.domain;

import au.twc.core.product.dynamo.converter.AttributeValuesConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@DynamoDBDocument
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false, onlyExplicitlyIncluded = true)
public class AttributeGroup implements Serializable {

    @DynamoDBAttribute(attributeName = "attribute_group")
    private String attribute_group;

    @DynamoDBAttribute(attributeName = "is_obsolete")
    @Builder.Default
    private Boolean is_obsolete = false;

    @DynamoDBAttribute(attributeName = "attributes")
    @DynamoDBTypeConverted(converter = AttributeValuesConverter.class)
    @Builder.Default
    private Map<String, AttributeValue> attributes = new HashMap<>();
}
