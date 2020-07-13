package de.share_now.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.share_now.model.Polygon;
import de.share_now.model.Tuple;
import de.share_now.model.Vehicle;

import org.locationtech.jts.geom.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static de.share_now.utils.Constants.CACHE_CARS;
import static de.share_now.utils.Constants.POLYGON_JSON;
import static de.share_now.utils.Constants.UTF_FORMAT;
import static de.share_now.utils.PolygonUtils.isInsidePoly;

/**
 *
 */
@Component
public class GeoUtils {
    private static final Logger logger = LoggerFactory.getLogger(GeoUtils.class);

    public static final ObjectMapper objectMapper = new ObjectMapper();


    /**
     * Method to fetch Vehicles list from the given rest API
     */
    public static List<Vehicle> getVehicleList(String vehicleUrl, String statusUrl) {
        RestTemplate restTemplate = new RestTemplate();
        if (!HttpStatus.OK.equals(restTemplate.exchange(statusUrl, HttpMethod.GET, null, String.class).getStatusCode())) {
            throw new RuntimeException("Car2Go server is down");
        }
        return restTemplate.exchange(vehicleUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Vehicle>>() {
                }).getBody();


    }

    /**
     * Method to get the polygon details from the git hub url
     */
    public static List<Polygon> getPolygonList() {
        try {
            Resource resource = new ClassPathResource(POLYGON_JSON);
            InputStream inputStream = resource.getInputStream();
            Reader reader = new InputStreamReader(inputStream, UTF_FORMAT);
            return objectMapper.readValue(reader, new TypeReference<List<Polygon>>() {
            });
        } catch (IOException e) {
            logger.warn("Unable to process the request for the Id {}");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No polygon file found");
        }

    }

    /**
     * Method to build polygons List
     */
    public static List<Polygon> buildGeoList(List<Vehicle> vehicleList, List<Polygon> finalPolygonList) {
        return vehicleList.stream().map(vehicle -> {
            Optional<Polygon> poly = finalPolygonList.stream()
                    .filter(polygon -> polygon.get_id().equalsIgnoreCase(vehicle.getPolygonId()))
                    .findAny();

            if (poly.isPresent()) {
                Polygon polygon = poly.get();
                if (polygon.getVin() != null) {
                    polygon.getVin().add(vehicle.getVin());
                } else {
                    Set val = new HashSet<>();
                    val.add(vehicle.getVin());
                    polygon.setVin(val);
                }
                return polygon;
            } else {
                return null;
            }
        }).filter(pp -> pp != null).collect(Collectors.toList());

    }

    /**
     * Method to build vehicles list
     */
    public static List<Vehicle> buildVehicleList(List<Vehicle> vehicleResponse, List<Polygon> finalPolygonList) {
        validateCarOrPolygon(vehicleResponse, finalPolygonList);
        return vehicleResponse.stream().map(vehicle -> {
            Optional<String> polygonId = finalPolygonList.stream().filter(e -> isInsidePoly(buildPolyCoordinates(e).toArray(new Coordinate[]{}),
                    vehicle.getPosition().getLongitude(),
                    vehicle.getPosition().getLatitude())).map(Polygon::get_id).findAny();
            if (polygonId.isPresent())
                vehicle.setPolygonId(polygonId.get());
            return vehicle;
        }).filter(pp -> pp.getPolygonId() != null).collect(Collectors.toList());
    }

    private static void validateCarOrPolygon(List<Vehicle> vehicleResponse, List<Polygon> finalPolygonList) {
        if ((vehicleResponse != null && vehicleResponse.size() <= 0)
                || (finalPolygonList != null && finalPolygonList.size() <= 0))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data found");
    }
    /**
     * Method to build polygon coordinates
     */
    private static List<Coordinate> buildPolyCoordinates(Polygon resultMap) {
        if (resultMap != null && resultMap.getGeometry() != null
                && resultMap.getGeometry().getCoordinates() != null && resultMap.getGeometry().getCoordinates().size() > 0) {
            return
                    resultMap.getGeometry().getCoordinates().get(0)
                            .stream().map(a -> new Coordinate(a.get(0), a.get(1)))
                            .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     *
     * @param cacheManager
     * @return
     */
    public static Tuple getCacheObject(CacheManager cacheManager) {
        Tuple tuple = (Tuple) cacheManager.getCache(CACHE_CARS).get(CACHE_CARS).get();
        if (tuple == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error occurred while fetching data from cache ");
        return tuple;
    }
}
