package au.twc.core.product.dynamo.converter;

import au.twc.core.product.domain.AttributeGroup;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class AttributeGroupsConverter<I extends AttributeGroup, T extends Map> implements DynamoDBTypeConverter<String, T> {
    private static final ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    private final Class<T> targetType;

    public AttributeGroupsConverter(Class<T> targetType) {
        this.targetType = targetType;
    }

    @Override
    public final String convert(final T object) {
        try {
            return mapper
                .writerFor(new TypeReference<Map<String, AttributeGroup>>() {
                })
                .writeValueAsString(object);
        } catch (final Exception e) {
            throw new DynamoDBMappingException("Unable to write object to JSON", e);
        }
    }

    @Override
    public final T unconvert(final String object) {
        try {
            return mapper
                .readerFor(new TypeReference<Map<String, AttributeGroup>>() {
                })
                .readValue(object);
        } catch (final Exception e) {
            throw new DynamoDBMappingException("Unable to read JSON string", e);
        }
    }
}
