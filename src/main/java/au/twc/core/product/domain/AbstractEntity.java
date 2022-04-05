package au.twc.core.product.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.Instant;

@SuperBuilder
@DynamoDBDocument
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractEntity implements Serializable {

    @DynamoDBAttribute(attributeName = "created_at")
    private Instant createdTime;

    @DynamoDBAttribute(attributeName = "updated_at")
    private Instant updateTime;

    @DynamoDBAttribute(attributeName = "modified_by")
    private String modifiedBy;

}
