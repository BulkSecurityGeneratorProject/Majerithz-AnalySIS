package com.empleodigital.murcia.majerithz.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.empleodigital.murcia.majerithz.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Location.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Department.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Department.class.getName() + ".employees", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Employee.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Employee.class.getName() + ".departments", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Company.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Company.class.getName() + ".locations", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Company.class.getName() + ".employees", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Company.class.getName() + ".analyses", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Company.class.getName() + ".assets", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Analysis.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Analysis.class.getName() + ".assets", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Asset.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Asset.class.getName() + ".assetSubTypes", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Asset.class.getName() + ".dimensions", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Asset.class.getName() + ".existingSafeguards", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Asset.class.getName() + ".threats", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Asset.class.getName() + ".dependences", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Asset.class.getName() + ".safeguardsPackages", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Asset.class.getName() + ".analyses", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.AssetType.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.AssetType.class.getName() + ".assetSubTypes", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.AssetSubType.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Dependence.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Dimension.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.ThreatType.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.ThreatType.class.getName() + ".threatSubTypes", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.ThreatSubType.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Threat.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.SafeguardType.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.SafeguardType.class.getName() + ".safeguardSubTypes", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.SafeguardSubType.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.ExistingSafeguards.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Safeguard.class.getName(), jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Safeguard.class.getName() + ".safeguardsPackages", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.Safeguard.class.getName() + ".existingSafeguards", jcacheConfiguration);
            cm.createCache(com.empleodigital.murcia.majerithz.domain.SafeguardsPackage.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
