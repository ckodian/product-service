package au.twc.core.product.repository;

import au.twc.core.product.domain.VariantProduct;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data MongoDB repository for the VariantProduct entity.
 */
@SuppressWarnings("unused")
@EnableScan
public interface VariantProductRepository extends CrudRepository<VariantProduct, String> {

    Optional<VariantProduct> findByBaseProductIdAndIdAndTenantId(String baseProductId, String variantId, String tenantId);

    public List<VariantProduct> findByBaseProductIdAndTenantIdAndDeleted(String baseProduct, String tenantId, Boolean deleted);

    Optional<VariantProduct> findByGtinAndTenantIdAndDeleted(String gtin, String tenantId, Boolean deleted);

    Optional<VariantProduct> findByProductRefAndTenantId(String productRef, String tenantId);

    Optional<VariantProduct> findByProductRefAndTenantIdAndDeleted(String productRef, String tenantId, Boolean deleted);

    Optional<VariantProduct> findByGtinAndBaseProductIdAndTenantIdAndDeleted(String gtin, String baseProductId, String tenantId, Boolean deleted);

    Optional<VariantProduct> findByGtinAndBaseProductRefAndTenantIdAndDeleted(String gtin, String baseProductRef, String tenantId, Boolean deleted);
    Optional<VariantProduct> findByProductRefAndBaseProductRefAndTenantIdAndDeleted(String productRef, String baseProductRef, String tenantId, Boolean deleted);

    Optional<VariantProduct> findByProductRefAndBaseProductIdAndTenantIdAndDeleted(String productRef, String baseProductId, String tenantId, Boolean deleted);

    List<VariantProduct> findByTenantIdAndDeleted(String tenantId, Boolean deleted);

    Optional<VariantProduct> findByIdAndTenantId(String variantId, String resolveTenant);

    Optional<VariantProduct> findByIdAndTenantIdAndDeleted(String id, String tenantId, Boolean deleted);
}
