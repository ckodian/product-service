package au.twc.core.product.repository;

import au.twc.core.product.domain.Product;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Product entity.
 */
@SuppressWarnings("unused")
@EnableScan
public interface ProductRepository extends CrudRepository<Product, String> {

    Optional<Product> findByIdAndTenantIdAndDeleted(String id, String tenantId, Boolean deleted);

    List<Product> findByTenantIdAndDeleted(String tenantId, Boolean deleted);

    Optional<Product> findByGtinAndTenantIdAndDeleted(String gtin, String tenantId, Boolean deleted);

    Optional<Product> findByProductRefAndTenantIdAndDeleted(String productRef, String tenantId, Boolean deleted);

    Optional<Product> findByIdAndTenantId(String id, String tenantId);

    Optional<Product> findByProductRefAndTenantId(String productRef, String tenantId);

    Optional<Product> findByProductRefAndGtinAndTenantIdAndDeleted(String productRef, String gtin, String tenantId, Boolean deleted);
}
