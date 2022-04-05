package au.twc.core.product.dto;

import au.twc.core.product.dynamo.converter.AttributeValuesConverter;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false, onlyExplicitlyIncluded = true)
public class AttributeGroupDto implements Serializable {

    private String attribute_group;

    @Builder.Default
    private Boolean is_obsolete = false;

    @Builder.Default
    private Map<String, AttributeValueDto> attributes = new HashMap<>();
}
