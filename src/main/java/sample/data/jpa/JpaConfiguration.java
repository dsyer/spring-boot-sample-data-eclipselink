package sample.data.jpa;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;

/**
 * @author Igor Mukhin
 */
@Configuration
public class JpaConfiguration extends JpaBaseConfiguration {
    
    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        EclipseLinkJpaVendorAdapter adapter = new EclipseLinkJpaVendorAdapter();
        return adapter;
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        
        map.put(PersistenceUnitProperties.WEAVING, detectWeavingMode());
        
        return map;
    }

    private String detectWeavingMode() {
        return InstrumentationLoadTimeWeaver.isInstrumentationAvailable() ? "true" : "static";
    }

}
