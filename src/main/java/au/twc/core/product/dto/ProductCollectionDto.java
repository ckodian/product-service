package au.twc.core.product.dto;

import au.twc.core.product.domain.enumeration.*;
import lombok.*;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * A ProductDto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ProductCollectionDto implements Serializable {

    private List<ProductDto> products;

    private List<VariantProductDto> variants;

}
