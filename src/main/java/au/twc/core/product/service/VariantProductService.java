package au.twc.core.product.service;

import au.twc.core.product.domain.VariantProduct;
import au.twc.core.product.dto.VariantProductDto;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface VariantProductService {
    /**
     * This method looks for a product by an ID
     *
     * @param id
     * @return Optional<VariantProductDto>
     */
    Optional<VariantProductDto> findVariantProductById(String id);

    /**
     * Find basic information on the variant product.
     * @param id
     * @return
     */
    Set<VariantProductDto> findVariantProductBasicInfoByBaseProduct(String id);

    /**
     * This method looks for a product by an ID
     *
     * @param productRef
     * @param baseProductRef
     * @return Optional<VariantProductDto>
     */
    Optional<VariantProductDto> findVariantProductByProductRefAndBaseProductRef(String productRef, String baseProductRef);

    /**
     * This method looks for a product by an ID
     *
     * @param variantId
     * @param baseProductId
     * @return Optional<VariantProductDto>
     */
    Optional<VariantProductDto> findVariantProductByIdAndBaseProductId(String variantId, String baseProductId);

    /**
     * This method looks for a product by an ID
     *
     * @param productRef
     * @return Optional<VariantProductDto>
     */
    Optional<VariantProductDto> findVariantProductByProductRef(String productRef);

    /**
     * This method looks for a product by its upc ID
     *
     * @param upc
     * @return Optional<VariantProductDto>
     */
    Optional<VariantProductDto> findVariantProductByUpc(String upc);

    /**
     * This method looks for a product by its isbn
     *
     * @param isbn
     * @return Optional<VariantProductDto>
     */
    Optional<VariantProductDto> findVariantProductByIsbn(String isbn);

    /**
     * This method looks for a product by its mpn ID
     *
     * @param mpn
     * @return Optional<VariantProductDto>
     */
    Optional<VariantProductDto> findVariantProductByMpn(String mpn);

    /**
     * This method looks for a product by its jan ID
     *
     * @param jan
     * @return Optional<VariantProductDto>
     */
    Optional<VariantProductDto> findVariantProductByJan(String jan);

    /**
     * This method looks for a product by its itf ID
     *
     * @param itf
     * @return Optional<VariantProductDto>
     */
    Optional<VariantProductDto> findVariantProductByItf(String itf);

    /**
     * This method looks for a product by its ean ID
     *
     * @param ean
     * @return Optional<VariantProductDto>
     */
    Optional<VariantProductDto> findVariantProductByEan(String ean);

    /**
     * This method looks for a product by its ean ID
     *
     * @param productId
     * @return List<VariantProductDto>
     */
    Set<VariantProductDto> findVariantsForProduct(String productId);


    Set<VariantProductDto> findVariantsForProductAndTenant(String productId, String tenant);

    /**
     * This method is responsible for creating a product, if the product already exist, then it will update the record.
     *
     * @param dto
     * @param productId
     * @return Optional<VariantProductDto>
     */
    Optional<VariantProductDto> createOrUpdateVariantProduct(VariantProductDto dto, String productId);

    Optional<VariantProductDto> createVariantProduct(VariantProductDto dto);

    /**
     * This method is responsible for creating a product, if the product already exist, then it will update the record.
     *
     * @param dto
     * @return Optional<VariantProductDto>
     */
    Optional<VariantProductDto> createOrUpdateVariantProduct(VariantProductDto dto);

    /**
     * This method is responsible for creating a product, if the product already exist, then it will update the record.
     *
     * @param dtos
     * @return Optional<VariantProductDto>
     */
    void createOrUpdateVariantProducts(Set<VariantProductDto> dtos, String baseProductId, String baseProductRef);

    /**
     * This method is responsible for creating a product, if the product already exist, then it will update the record.
     *
     * @param productId
     * @return Optional<VariantProductDto>
     */
    List<VariantProductDto> findAllVariantsForProduct(String productId);

    /**
     * Lookup a product by its particulars
     *
     * @param dto
     * @return List<VariantProductDto>
     */
    List<VariantProductDto> lookupVariantProduct(VariantProductDto dto);

    /**
     * find all products
     *
     * @return List<VariantProductDto>
     */
    List<VariantProductDto> findAll();

    /**
     * Remove a variant by ID
     *
     * @param id
     */
    void removeVariantById(String id);

    void removeVariantByBaseProductIdAndVariantId(String productId, String variantId);

    /**
     * Remove a variant by ID
     *
     * @param baseProductRef
     * @param variantRef
     */
    void removeVariantByRef(String baseProductRef, String variantRef);

    /**
     * Remove a variants of a product
     *
     * @param baseProductId
     */
    void removeVariantsByBaseProduct(String baseProductId);

    /**
     * Search products
     *
     * @param query
     * @return List<VariantProductDto>
     */
    List<VariantProductDto> searchProduct(String query);

    void removeVariantsByBaseProductAndTenantId(String baseProductId, String tenantId);

    void uploadVariantProducts(List<VariantProductDto> variants);

}
