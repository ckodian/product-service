package au.twc.core.product.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.*;

import javax.annotation.Generated;
import java.io.Serializable;

@DynamoDBDocument
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantOptions implements Serializable {

    public static final long serialVersionUID = 2421451536796560253L;

    private String optionId;

    private String optionName;

}
