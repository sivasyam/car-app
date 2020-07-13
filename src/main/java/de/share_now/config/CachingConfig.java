package de.share_now.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@ConditionalOnProperty(name = "spring.cache.names")
@EnableCaching
public class CachingConfig {

    @Value("${spring.cache.names}")
    public String[] cacheNames;


    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("cars");
    }
/*
    @ConditionalOnProperty(name = "spring.cache.autoexpiry", value = "true")
    @Scheduled(fixedDelayString = "${spring.cache.expire.delay:500000}")
    public void cacheEvict() {
        cacheManager.getCacheNames().stream()
                .map(cacheManager::getCache)
                .forEach(Cache::clear);
    }
*/
}
