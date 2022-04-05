package au.twc.core.product.dynamo.converter;

import au.twc.core.product.domain.AttributeGroup;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AttributeGroupConverter<T extends AttributeGroup> implements DynamoDBTypeConverter<String, T> {
    private static final ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    private final Class<T> targetType;

    public AttributeGroupConverter(Class<T> targetType) {
        this.targetType = targetType;
    }

    @Override
    public final String convert(final T object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (final Exception e) {
            throw new DynamoDBMappingException("Unable to write object to JSON", e);
        }
    }

    @Override
    public final T unconvert(final String object) {
        try {
            return mapper.readValue(object, targetType);
        } catch (final Exception e) {
            throw new DynamoDBMappingException("Unable to read JSON string", e);
        }
    }
}
