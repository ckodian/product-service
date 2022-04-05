package au.twc.core.product.dynamo.converter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InstantJson {
    Integer nano;
    Long epochSecond;
    InstantJson(Integer nano, Long epochSecond) {
        this.epochSecond = epochSecond;
        this.nano = nano;
    }
}
