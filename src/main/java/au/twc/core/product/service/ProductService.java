package au.twc.core.product.service;

import au.twc.core.product.domain.Product;
import au.twc.core.product.dto.ProductDto;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    /**
     * This method looks for a product by an ID
     * @param id
     * @return
     */
    Optional<ProductDto> findProductById(String id);

    /**
     * This method looks for a product by an ID
     * @param productRef
     * @return
     */
    Optional<ProductDto> findProductByRef(String productRef);

    /**
     * This method is used to return basic information of the product
     * @param id
     * @return
     */
    Optional<ProductDto> findProductBasicInfo(String id);

    /**
     * This method looks for a product by its upc ID
     * @param upc
     * @return
     */
    Optional<ProductDto> findProductByUpc(String upc);

    /**
     * This method looks for a product by its isbn
     * @param isbn
     * @return
     */
    Optional<ProductDto> findProductByIsbn(String isbn);

    /**
     * This method looks for a product by its mpn ID
     * @param mpn
     * @return
     */
    Optional<ProductDto> findProductByMpn(String mpn);

    /**
     * This method looks for a product by its jan ID
     * @param jan
     * @return
     */
    Optional<ProductDto> findProductByJan(String jan);

    /**
     * This method looks for a product by its itf ID
     * @param itf
     * @return
     */
    Optional<ProductDto> findProductByItf(String itf);

    /**
     * This method looks for a product by its upi ID
     * @param upi
     * @return
     */
    Optional<ProductDto> findProductByUpi(String upi);

    /**
     * This method looks for a product by its ean ID
     * @param ean
     * @return
     */
    Optional<ProductDto> findProductByEan(String ean);

    /**
     * This method is responsible for creating a product, if the product does not  then it will update the record.
     * @param productDto
     * @return
     */
    Optional<ProductDto> createProduct(ProductDto productDto);


    /**
     * This method is responsible for updating a product, if the product already exist, then it will update the record.
     * @param productDto
     * @return
     */
    Optional<ProductDto> updateProduct(ProductDto productDto);

    /**
     * Lookup a product by its particulars
     * @param productDto
     * @return
     */
    List<ProductDto> lookupProduct(ProductDto productDto);

    /**
     * At times a customer might scan in a product that is not available at present in the system, and this will trigger and event to
     * create an unverified product, which require action by either system or manually to mark as verified or approved.
     * @param productDto
     * @return
     */
    Optional<ProductDto> createAnUnverifiedProduct(ProductDto productDto);

    /**
     * find all products
     * @return
     */
    List<ProductDto> findAll();

    /**
     * Remove a particular product from repository
     * @param id
     */
    void removeProductById(String id);

    /**
     * Remove a particular product from repository
     * @param productRef
     */
    void removeProductByProductRef(String productRef);

    void uploadProducts(List<ProductDto> productDtoList);

//    /**
//     * Search products
//     * @param query
//     * @return
//     */
//    List<ProductDto> searchProduct(String query);
}
