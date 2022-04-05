package au.twc.core.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
public class ProductValidatorDto implements Serializable {

    private static final long serialVersionUID = 3691789920440721086L;

    private ProductDto product;

    private VariantProductDto variantProduct;

    private Boolean variant;
}
