package au.twc.core.product.dto;

import au.twc.core.product.domain.AttributeValueType;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper=false, onlyExplicitlyIncluded = true)
public class AttributeValueDto implements Serializable {

    private String attribute_value;

    private AttributeValueType value_type;
}
