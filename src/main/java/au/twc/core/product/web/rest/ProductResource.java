package au.twc.core.product.web.rest;

import au.twc.core.product.domain.Product;
import au.twc.core.product.domain.VariantProduct;
import au.twc.core.product.dto.ProductCollectionDto;
import au.twc.core.product.dto.ProductDto;
import au.twc.core.product.dto.ProductValidatorDto;
import au.twc.core.product.dto.VariantProductDto;
import au.twc.core.product.service.ProductService;
import au.twc.core.product.service.VariantProductService;
import au.twc.core.product.web.rest.errors.BadRequestAlertException;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link au.twc.core.product.domain.Product}.
 */
@RestController
@RequestMapping("/api/v2")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private static final String ENTITY_NAME = "productsvcProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductService productService;

    private final VariantProductService variantProductService;

    public ProductResource(ProductService productService, VariantProductService variantProductService) {
        this.productService = productService;
        this.variantProductService = variantProductService;
    }

    /**
     * {@code POST  /products} : Create a new product.
     *
     * @param product the product to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new product, or with status {@code 400 (Bad Request)} if the product has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto product) throws URISyntaxException {
        log.debug("REST request to save Product : {}", product);
        if (product.getId() != null) {
            throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Optional<ProductDto> result = productService.createProduct(product);
        return ResponseEntity.created(new URI("/api/products/" + result.get().getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.get().getId()))
            .body(result.get());
    }

    /**
     * {@code POST  /products/validate} : validate a product data.
     *
     * @param product the product to create.
     * @return the {@link ResponseEntity} with status {@code 204 (OK)} and with body product, or with status {@code 400 (Bad Request)} if the product is invalid.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/products/validate")
    public ResponseEntity<Void> validateRequest(@Valid @RequestBody ProductDto product) throws URISyntaxException {
        return ResponseEntity.accepted().build();
    }

    /**
     * Uploads multiple products
     *
     * @param productDtoList
     * @return
     */
    @PostMapping("/uploadProducts")
    public ResponseEntity<String> uploadProducts(@Valid @RequestBody List<ProductDto> productDtoList) {
        log.debug("REST request to upload Products : {}", productDtoList);
        for (ProductDto product : productDtoList) {
            if (product.getId() != null) {
                throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
            }
        }
        productService.uploadProducts(productDtoList);
        return ResponseEntity.ok().build();
    }

    /**
     * {@code PUT  /products} : Updates an existing product.
     *
     * @param product the product to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated product,
     * or with status {@code 400 (Bad Request)} if the product is not valid,
     * or with status {@code 500 (Internal Server Error)} if the product couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/products")
    public ResponseEntity<ProductDto> updateProduct(@Valid @RequestBody ProductDto product) throws URISyntaxException {
        log.debug("REST request to update Product : {}", product);

        if (product.getId() == null
            && product.getProductRef() == null
            && product.getGtin() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<ProductDto> result = productService.updateProduct(product);

        if (result.isPresent()) {
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.get().getId()))
                .body(result.get());
        }

        return ResponseUtil.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /products} : get all the products.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of products in body.
     */
//    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        log.debug("REST request to get all Products");
        return productService.findAll();
    }

    /**
     * {@code GET  /products/:id} : get the "id" product.
     *
     * @param id the id of the product to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the product, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products/validate/{id}")
    public ResponseEntity<ProductValidatorDto> validateProduct(@PathVariable String id) {
        log.debug("REST request to get Product : {}", id);
        Optional<ProductDto> product = productService.findProductById(id);

        if (product.isPresent()) {
            ProductValidatorDto productValidatorDto = ProductValidatorDto
                .builder()
                .product(product.get())
                .variant(false)
                .build();
            return ResponseUtil.wrapOrNotFound(Optional.of(productValidatorDto));
        }

        Optional<VariantProductDto> vProduct = variantProductService.findVariantProductById(id);

        if (vProduct.isPresent()) {
            ProductValidatorDto productValidatorDto = ProductValidatorDto
                .builder()
                .variantProduct(vProduct.get())
                .variant(true)
                .build();
            return ResponseUtil.wrapOrNotFound(Optional.of(productValidatorDto));
        }

        return ResponseUtil.wrapOrNotFound(Optional.empty());
    }

    @GetMapping("/products/validateRef/{ref}")
    public ResponseEntity<ProductValidatorDto> validateProductRef(@PathVariable String ref) {
        log.debug("REST request to get Product : {}", ref);
        Optional<ProductDto> product = productService.findProductByRef(ref);

        if (product.isPresent()) {
            ProductValidatorDto productValidatorDto = ProductValidatorDto
                .builder()
                .product(product.get())
                .variant(false)
                .build();
            return ResponseUtil.wrapOrNotFound(Optional.of(productValidatorDto));
        }

        Optional<VariantProductDto> vProduct = variantProductService.findVariantProductByProductRef(ref);

        if (vProduct.isPresent()) {
            ProductValidatorDto productValidatorDto = ProductValidatorDto
                .builder()
                .variantProduct(vProduct.get())
                .variant(true)
                .build();
            return ResponseUtil.wrapOrNotFound(Optional.of(productValidatorDto));
        }

        return ResponseUtil.wrapOrNotFound(Optional.empty());
    }

    /**
     * {@code GET  /products/:id} : get the "id" product.
     *
     * @param id the id of the product to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the product, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable String id) {
        log.debug("REST request to get Product : {}", id);
        Optional<ProductDto> product = productService.findProductById(id);
        return ResponseUtil.wrapOrNotFound(product);
    }

    /**
     * {@code GET  /products/:productRef} : get the "id" product.
     *
     * @param productRef the id of the product to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the product, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products/{productRef}/byref")
    public ResponseEntity<ProductDto> getProductByRef(@PathVariable String productRef) {
        log.debug("REST request to get Product : {}", productRef);
        Optional<ProductDto> product = productService.findProductByRef(productRef);
        return ResponseUtil.wrapOrNotFound(product);
    }

    /**
     * {@code DELETE  /products/:id} : delete the "id" product.
     *
     * @param id the id of the product to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        log.debug("REST request to delete Product : {}", id);
        productService.removeProductById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code DELETE  /products/:productRef} : delete the "id" product.
     *
     * @param productRef the id of the product to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/products/{productRef}/byref")
    public ResponseEntity<Void> deleteProductByExternalRef(@PathVariable String productRef) {
        log.debug("REST request to delete Product : {}", productRef);
        productService.removeProductByProductRef(productRef);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, productRef))
            .build();
    }

    @GetMapping("/products/lookup")
    public ResponseEntity<ProductCollectionDto> lookupProduct(@RequestParam(value = "productRef", required = false) String productRef,
                                                              @RequestParam(value = "gtin", required = false) String gtin) {
        if (StringUtils.isEmpty(productRef) &&
            StringUtils.isEmpty(gtin)) {
            return ResponseEntity.notFound().build();
        }
        ProductCollectionDto productResponse = ProductCollectionDto.builder().build();
        ProductDto searchCriteria = ProductDto.builder()
            .productRef(productRef)
            .gtin(gtin)
            .build();

        List<ProductDto> products = productService.lookupProduct(searchCriteria);
        productResponse.setProducts(products);

        VariantProductDto variantCriteria = VariantProductDto.builder()
            .productRef(productRef)
            .gtin(gtin)
            .build();
        List<VariantProductDto> variants = variantProductService.lookupVariantProduct(variantCriteria);
        productResponse.setVariants(variants);

        return ResponseEntity.ok(productResponse);
    }

//    /**
//     * {@code SEARCH  /_search/products?query=:query} : search for the product corresponding
//     * to the query.
//     *
//     * @param query the query of the product search.
//     * @return the result of the search.
//     */
//    @GetMapping("/_search/products")
//    public List<ProductDto> searchProducts(@RequestParam String query) {
//        log.debug("REST request to search Products for query {}", query);
//        return productService.searchProduct(query);
//    }
}
