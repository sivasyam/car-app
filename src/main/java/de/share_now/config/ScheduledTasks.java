package de.share_now.config;

import de.share_now.model.Tuple;
import de.share_now.services.VehicleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static de.share_now.utils.Constants.CACHE_CARS;

/**
 *
 */
@Configuration
@Component
public class ScheduledTasks implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    VehicleService myService;

    @Autowired
    CacheManager cacheManager;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        logger.info("Fetching cache after startup");
        Tuple cars = myService.fetchCars();
        cacheManager.getCache(CACHE_CARS).put(CACHE_CARS, cars);
    }
}
