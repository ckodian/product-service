package au.twc.core.product.dynamo.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;

public class InstantConverter<T extends Instant> implements DynamoDBTypeConverter<String, T> {
    private static final ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    private final Class<T> targetType;

    public InstantConverter(Class<T> targetType) {
        this.targetType = targetType;
    }

    @Override
    public final String convert(final T object) {
        try {
            return mapper
                .writeValueAsString(object);
        } catch (final Exception e) {
            throw new DynamoDBMappingException("Unable to write object to JSON", e);
        }
    }

    @Override
    public final T unconvert(final String object) {
        try {

            InstantJson iJson = mapper
                .readerFor(InstantJson.class)
                .readValue(object);
            return (T) Instant.ofEpochSecond(iJson.epochSecond, iJson.nano);

        } catch (final Exception e) {
            throw new DynamoDBMappingException("Unable to read JSON string", e);
        }
    }
}
