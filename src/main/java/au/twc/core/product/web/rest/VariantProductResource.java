package au.twc.core.product.web.rest;

import au.twc.core.product.domain.VariantProduct;
import au.twc.core.product.dto.ProductDto;
import au.twc.core.product.dto.VariantProductDto;
import au.twc.core.product.repository.VariantProductRepository;
import au.twc.core.product.service.ProductService;
import au.twc.core.product.service.VariantProductService;
import au.twc.core.product.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing {@link VariantProduct}.
 */
@RestController
@RequestMapping("/api/v2")
public class VariantProductResource {

    private final Logger log = LoggerFactory.getLogger(VariantProductResource.class);

    private static final String ENTITY_NAME = "productsvcVariantProduct";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VariantProductRepository variantProductRepository;

    private final VariantProductService variantProductService;

    private final ProductService productService;

    public VariantProductResource(VariantProductRepository variantProductRepository,
                                  VariantProductService variantProductService, ProductService productService) {
        this.variantProductRepository = variantProductRepository;
        this.variantProductService = variantProductService;
        this.productService = productService;
    }

    /**
     * {@code POST  /products/{productId}/variants} : Create a new variantProduct.
     *
     * @param variantProduct the variantProduct to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new variantProduct, or with status {@code 400 (Bad Request)} if the variantProduct has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/products/variants")
    public ResponseEntity<VariantProductDto> createVariantProduct(@RequestBody VariantProductDto variantProduct) throws URISyntaxException {
        log.debug("REST request to save VariantProduct : {}", variantProduct);
        if (variantProduct.getId() != null) {
            throw new BadRequestAlertException("A new variantProduct cannot already have an ID", ENTITY_NAME, "idexists");
        }

        if (variantProduct != null
            && StringUtils.isBlank(variantProduct.getBaseProductRef())
            && StringUtils.isBlank(variantProduct.getBaseProductId())) {
            throw new BadRequestAlertException("Product id cannot be empty", ENTITY_NAME, "productIdDontExists");
        }

        Optional<VariantProductDto> result = variantProductService.createVariantProduct(variantProduct);

        return ResponseEntity.created(new URI("/product/".concat("variants/").concat(result.get().getId())))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.get().getId()))
            .body(result.get());
    }

    /**
     * {@code POST  /products/variants/validate} : validate a variantProduct.
     *
     * @param variantProduct the variantProduct to validate.
     * @return the {@link ResponseEntity} with status {@code 202 (Accepted)} and with body the new variantProduct, or with status {@code 400 (Bad Request)} if the variantProduct is invalid.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/products/variants/validate")
    public ResponseEntity<Void> validateVariantProduct(@RequestBody VariantProductDto variantProduct) throws URISyntaxException {
        log.debug("REST request to save VariantProduct : {}", variantProduct);

        if (variantProduct != null
            && StringUtils.isBlank(variantProduct.getBaseProductRef())
            && StringUtils.isBlank(variantProduct.getBaseProductId())) {
            throw new BadRequestAlertException("Product id cannot be empty", ENTITY_NAME, "productIdDontExists");
        }

        return ResponseEntity.accepted().build();
    }

    /**
     * {@code PUT  /products/{productId}/variants} : Updates an existing variantProduct.
     *
     * @param variantProduct the variantProduct to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated variantProduct,
     * or with status {@code 400 (Bad Request)} if the variantProduct is not valid,
     * or with status {@code 500 (Internal Server Error)} if the variantProduct couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/products/variants")
    public ResponseEntity<VariantProductDto> updateVariantProduct(@RequestBody VariantProductDto variantProduct) throws URISyntaxException {
        log.debug("REST request to update VariantProduct : {}", variantProduct);

        if (StringUtils.isBlank(variantProduct.getProductRef())
            && StringUtils.isBlank(variantProduct.getId())) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<VariantProductDto> result = variantProductService.createOrUpdateVariantProduct(variantProduct);

        if (result.isPresent()) {
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.get().getId()))
                .body(result.get());
        }

        return ResponseEntity.notFound()
            .headers(HeaderUtil.createFailureAlert(applicationName, false, ENTITY_NAME, "invalidVariantId", "Invalid Variant ID"))
            .build();

    }

    /**
     * {@code GET  /products/{productId}/variants} : get all the variantProducts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of variantProducts in body.
     */
    @GetMapping("/products/{productId}/variants")
    public Set<VariantProductDto> getAllVariantProducts(@PathVariable String productId) {
        log.debug("REST request to get all VariantProducts");
        if (StringUtils.isBlank(productId)) {
            throw new BadRequestAlertException("Invalid Product Id id", ENTITY_NAME, "idnull");
        }

        return variantProductService.findVariantsForProduct(productId);
    }

    /**
     * {@code GET  /products/{productId}/variants/{id}:productId, id} : get the "id" variantProduct.
     *
     * @param id the id of the variantProduct to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the variantProduct, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products/{productId}/variants/{id}")
    public ResponseEntity<VariantProductDto> getVariantProduct(@PathVariable String productId, @PathVariable String id) {
        log.debug("REST request to get VariantProduct : {}, for product", id, productId);

        if (StringUtils.isBlank(productId) || StringUtils.isBlank(id)) {
            throw new BadRequestAlertException("Invalid request", ENTITY_NAME, "idnull");
        }

        //TODO we are calling findByProductRefAndBaseProductRef but passing variable with different names. myt cause confusion
        Optional<VariantProductDto> variantProduct = variantProductService.findVariantProductByIdAndBaseProductId(id, productId);
        return ResponseUtil.wrapOrNotFound(variantProduct);
    }

    /**
     * {@code GET  /products/{productId}/variants/{id}:productId, id} : get the "id" variantProduct.
     *
     * @param id the id of the variantProduct to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the variantProduct, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products/variants/{id}")
    public ResponseEntity<VariantProductDto> getVariantProduct(@Valid @PathVariable String id) {
        log.debug("REST request to get VariantProduct : {}, for id", id);

        if (StringUtils.isBlank(id)) {
            throw new BadRequestAlertException("Invalid request", ENTITY_NAME, "idnull");
        }

        Optional<VariantProductDto> variantProduct = variantProductService.findVariantProductById(id);
        return ResponseUtil.wrapOrNotFound(variantProduct);
    }

    /**
     * {@code GET  /products/{productId}/variants/{id}:productId, id} : get the "id" variantProduct.
     *
     * @param variantRef the id of the variantProduct to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the variantProduct, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/products/variants/{variantRef}/byref")
    public ResponseEntity<VariantProductDto> getVariantProductByRef(@Valid @PathVariable String variantRef) {
        log.debug("REST request to get VariantProduct : {}, for reference", variantRef);

        if (StringUtils.isBlank(variantRef)) {
            throw new BadRequestAlertException("Invalid request", ENTITY_NAME, "refnull");
        }

        Optional<VariantProductDto> variantProduct = variantProductService.findVariantProductByProductRef(variantRef);
        return ResponseUtil.wrapOrNotFound(variantProduct);
    }

    /**
     * {@code DELETE  /variant-products/:id} : delete the "id" variantProduct.
     *
     * @param productId the id of the baseProduct.
     * @param variantId the id of the variantProduct to be deleted.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/products/{productId}/variants/{variantId}")
    public ResponseEntity<Void> deleteVariantProduct(@Valid @PathVariable String productId, @Valid @PathVariable String variantId) {
        log.debug("REST request to delete VariantProduct : {} for Product {}", variantId, productId);
        variantProductService.removeVariantByBaseProductIdAndVariantId(productId, variantId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, variantId)).build();
    }

    /**
     * {@code DELETE  /variant-products/:id} : delete the "id" variantProduct.
     *
     * @param variantRef the ref of the variantProduct to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/products/{baseProductRef}/variants/{variantRef}/byref")
    public ResponseEntity<Void> deleteVariantProductbyRef(@Valid @PathVariable String baseProductRef, @Valid @PathVariable String variantRef) {
        log.debug("REST request to delete VariantProduct : {} for Product {}", baseProductRef, variantRef);
        variantProductService.removeVariantByRef(baseProductRef, variantRef);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, variantRef)).build();
    }

    /**
     * Uploads multiple products
     * @param variantProducts
     * @return
     */
    @PostMapping("/uploadVariants")
    public ResponseEntity<Void> uploadProducts(@Valid @RequestBody List<VariantProductDto> variantProducts){
        log.debug("REST request to upload Products : {}", variantProducts);

        if(CollectionUtils.isEmpty(variantProducts)) {
            return ResponseEntity.badRequest().build();
        }
        variantProductService.uploadVariantProducts(variantProducts);
        return ResponseEntity.ok().build();
    }
}
