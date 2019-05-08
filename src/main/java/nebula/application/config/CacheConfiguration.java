package nebula.application.config;

import nebula.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
                jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
                CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                        ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                        .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, nebula.application.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, nebula.application.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, nebula.application.domain.User.class.getName());
            createCache(cm, nebula.application.domain.Authority.class.getName());
            createCache(cm, nebula.application.domain.User.class.getName() + ".authorities");
      //      createCache(cm, io.github.jhipster.sample.domain.PersistentToken.class.getName());
            createCache(cm, nebula.application.domain.User.class.getName() + ".persistentTokens");
     //       createCache(cm, io.github.jhipster.sample.domain.BankAccount.class.getName());
     //       createCache(cm, io.github.jhipster.sample.domain.BankAccount.class.getName() + ".operations");
     //       createCache(cm, io.github.jhipster.sample.domain.Label.class.getName());
     //       createCache(cm, io.github.jhipster.sample.domain.Label.class.getName() + ".operations");
     //       createCache(cm, io.github.jhipster.sample.domain.Operation.class.getName());
      //      createCache(cm, io.github.jhipster.sample.domain.Operation.class.getName() + ".labels");
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}