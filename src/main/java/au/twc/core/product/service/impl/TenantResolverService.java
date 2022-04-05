package au.twc.core.product.service.impl;

import au.twc.core.product.TWCConstants;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class TenantResolverService {

    public String resolveTenant() {
        if (!ObjectUtils.isEmpty(RequestContextHolder.currentRequestAttributes())) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String tenant = request.getHeader("X-TWC-Tenant");
            return tenant != null ? tenant : TWCConstants.TWC_DEFAULT_CLIENT;
        }
        return TWCConstants.TWC_DEFAULT_CLIENT;
    }
}
