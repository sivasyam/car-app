package de.share_now.services.impl;

import de.share_now.model.Polygon;
import de.share_now.model.Tuple;
import de.share_now.model.Vehicle;
import de.share_now.services.VehicleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static de.share_now.utils.Constants.CACHE_CARS;
import static de.share_now.utils.Constants.CARS;
import static de.share_now.utils.Constants.POLYGON;
import static de.share_now.utils.GeoUtils.buildGeoList;
import static de.share_now.utils.GeoUtils.buildVehicleList;
import static de.share_now.utils.GeoUtils.getCacheObject;
import static de.share_now.utils.GeoUtils.getPolygonList;
import static de.share_now.utils.GeoUtils.getVehicleList;

/**
 * Vehicle services
 */
@Service
@Component
public class VehicleServiceImpl implements VehicleService {
    private static final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

    @Value("${share.now.car2go.Stuttgart.url}")
    public String vehicleUrl;

    @Value("${share.now.car2go.status.url}")
    public String statusUrl;

    @Autowired
    CacheManager cacheManager;

    /**
     *
     */
    @Scheduled(fixedDelayString = "${share.now.cache.refresh}")
    @Override
    public void refreshCache() {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            logger.warn("Thread interrupted");
        }
        logger.info("refreshing cache");
        Tuple cars = fetchCars();
        cacheManager.getCache(CACHE_CARS).put(CACHE_CARS, cars);
    }

    private void evictAllCaches() {
        cacheManager.getCache(CACHE_CARS).clear();
    }

    @Override
    public Tuple fetchAllCars() {
        return getCacheObject(cacheManager);
    }

    /**
     *
     */
    @Override
    public Tuple fetchCars() {
        try {
            List<Polygon> finalPolygonList = getPolygonList();
            List<Vehicle> vehicleList = buildVehicleList(getVehicleList(vehicleUrl, statusUrl), finalPolygonList);
            logger.info("Cars Size {}, Polygon Size {}", vehicleList.size(), finalPolygonList.size());
            Tuple tuple = Tuple.builder().vehicles(new ArrayList()).polygons(new ArrayList()).build();

            tuple.getVehicles().addAll(vehicleList);
            List<Polygon> polygons = buildGeoList(vehicleList, finalPolygonList);
            tuple.getPolygons().addAll(polygons);

            return tuple;
        } catch (Exception e) {
            logger.warn("Unable to fetch the details, Please check the Car2Go server status", e);
            throw new RuntimeException("Unable to process the cache, Please check the Car2Go server status");
        }
    }

    /**
     *
     */
    @Override
    public Vehicle getVehicle(String vin) {
        Optional<Vehicle> vehicle = getCacheObject(cacheManager).getVehicles()
                .stream()
                .filter(p -> p.getVin().equalsIgnoreCase(vin))
                .findAny();
        validateObject(vehicle.isPresent(), CARS);
        return vehicle.get();
    }

    /**
     *
     */
    @Override
    public Polygon getPolygon(String polygonId) {
        Optional<Polygon> geoCustomObject = getCacheObject(cacheManager).getPolygons()
                .stream()
                .filter(p -> p.get_id().equalsIgnoreCase(polygonId))
                .findAny();

        validateObject(geoCustomObject.isPresent(), POLYGON);
        return geoCustomObject.get();

    }

    @Override
    public Tuple getCarOrPolygon(Map<String, String> _filter) {
        return null;
    }

    /**
     *
     */
    private void validateObject(boolean isPresent, String type) {
        if (!isPresent)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No " + type + " found ");
    }

}
