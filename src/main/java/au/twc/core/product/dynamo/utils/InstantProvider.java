package au.twc.core.product.dynamo.utils;

import org.springframework.data.auditing.DateTimeProvider;

import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

public enum InstantProvider implements DateTimeProvider {

    INSTANCE;

    /*
     * (non-Javadoc)
     * @see org.springframework.data.auditing.DateTimeProvider#getNow()
     */
    @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(Instant.now());
    }
}
