package au.twc.core.product.dto;

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
public class VariantOptionsDto implements Serializable {

    public static final long serialVersionUID = 2421451536796560253L;

    private String optionId;

    private String optionName;

}
