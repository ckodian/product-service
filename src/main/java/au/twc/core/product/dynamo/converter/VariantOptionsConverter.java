package au.twc.core.product.dynamo.converter;

import au.twc.core.product.domain.VariantOptions;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Set;

public class VariantOptionsConverter<I extends VariantOptions, T extends Set> implements DynamoDBTypeConverter<String, T> {
    private static final ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    private final Class<T> targetType;

    public VariantOptionsConverter(Class<T> targetType) {
        this.targetType = targetType;
    }

    @Override
    public final String convert(final T object) {
        try {
            return mapper
                .writerFor(new TypeReference<Set<VariantOptions>>() {})
                .writeValueAsString(object);
        } catch (final Exception e) {
            throw new DynamoDBMappingException("Unable to write object to JSON", e);
        }
    }

    @Override
    public final T unconvert(final String object) {
        try {
            return mapper
                .readerFor(new TypeReference<Set<VariantOptions>>() {})
                .readValue(object);
        } catch (final Exception e) {
            throw new DynamoDBMappingException("Unable to read JSON string", e);
        }
    }
}
