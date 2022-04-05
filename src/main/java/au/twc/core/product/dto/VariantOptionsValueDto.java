package au.twc.core.product.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.*;

import java.io.Serializable;

@DynamoDBDocument
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VariantOptionsValueDto implements Serializable {

    public static final long serialVersionUID = 2421451536796560253L;

    @NonNull
    private String optionId;

    @NonNull
    private String optionValue;

    private String optionLabel;

    private String optionsImageId;

    private String optionDefaultImage;

}
