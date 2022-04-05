//package au.twc.core.product.config;
//
//import au.twc.core.product.TWCConstants;
//import au.twc.core.product.security.SecurityUtils;
//import org.keycloak.adapters.KeycloakConfigResolver;
//import org.keycloak.adapters.KeycloakDeployment;
//import org.keycloak.adapters.KeycloakDeploymentBuilder;
//import org.keycloak.adapters.spi.HttpFacade;
//import org.keycloak.representations.adapters.config.AdapterConfig;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.ObjectUtils;
//import org.springframework.util.StringUtils;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Configuration
//public class TenantBasedKeycloakConfigResolver implements KeycloakConfigResolver {
//
//    private static final Logger LOG = LoggerFactory.getLogger(TenantBasedKeycloakConfigResolver.class);
//
//    private Map<String, KeycloakDeployment> resolvedTenants = new HashMap<>();
//
//    @Value("${twc.keycloak.auth-server}")
//    private String authServerUrl;
//
//    @Value("${twc.keycloak.confidential-port}")
//    private String confidentialPort;
//
//    @Value("${twc.keycloak.default-tenant}")
//    private String defaultTenant;
//
//    @Value("${spring.security.oauth2.client.provider.oidc.issuer-uri}")
//    private String issuerUri;
//
//    @Override
//    public KeycloakDeployment resolve(HttpFacade.Request request) {
//
//        String tenant = request.getHeader("X-TWC-Tenant");
//        if(StringUtils.isEmpty(tenant)) {
//            tenant = defaultTenant;
//        }
//
//        LOG.debug("Resolved tenant {}", tenant);
//
//        //TODO possible to use an ehcache with a lifecycle management.
//        // Tenant is a store from the merchant, hence the possiblity of this causing a memory leak is very low
//        KeycloakDeployment kcCachedDeploy = resolvedTenants.get(tenant);
//        if(!ObjectUtils.isEmpty(kcCachedDeploy)) {
//            LOG.debug("Found keycloak deploy for tenant for {}", tenant);
//            return kcCachedDeploy;
//        }
//
//        AdapterConfig aConf = new AdapterConfig();
//        aConf.setRealm(tenant);
//        String resource = SecurityUtils.getCurrentUserLogin().isPresent() ? SecurityUtils.getCurrentUserLogin().get() : TWCConstants.TWC_DEFAULT_CLIENT;
//        aConf.setResource(resource);
//        aConf.setAuthServerUrl(authServerUrl);
//        aConf.setPublicClient(false);
//        aConf.setConfidentialPort(Integer.parseInt(confidentialPort));
//
//        LOG.debug("Building keycloak deploy for tenant {}", tenant);
//
//        KeycloakDeployment kcResolvedDeploy = KeycloakDeploymentBuilder.build(aConf);
//
//        resolvedTenants.put(tenant, kcResolvedDeploy);
//
//        return kcResolvedDeploy;
//    }
//}
