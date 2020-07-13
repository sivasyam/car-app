package de.share_now.controllers;

import de.share_now.model.Polygon;
import de.share_now.model.Vehicle;
import de.share_now.services.VehicleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static de.share_now.utils.Constants.POLYGON_ID;
import static de.share_now.utils.Constants.VIN;

/**
 * Controller responsible for vehicles and polygons
 */
@RestController
@RequestMapping(value = "/api/v1/")
@Api(value = "Cars and polygons rest service")
public class VehicleController {

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);

    @Autowired
    VehicleService vehicleService;

    /**
     * Get all cars list
     */
    @RequestMapping(value = "/car", method = RequestMethod.GET)
    @ApiOperation(value = "Get all cars list")
    @ApiResponses(value = {@ApiResponse(code = 200, response = List.class, message = "cars response received"),
            @ApiResponse(code = 404, message = "No cars found"), @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity<?> getCars() {
        logger.info("Processing get all cars request");
        return ResponseEntity.ok(vehicleService.fetchAllCars().getVehicles());
    }

    /**
     * Get all polygons
     */
    @RequestMapping(value = "/polygon", method = RequestMethod.GET)
    @ApiOperation(value = "Get all polygons list")
    @ApiResponses(value = {@ApiResponse(code = 200, response = List.class, message = "polygons response received"),
            @ApiResponse(code = 404, message = "No polygons found"), @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity<?> getPolygons() {
        logger.info("Processing get all polygons request");
        return ResponseEntity.ok(vehicleService.fetchAllCars().getPolygons());
    }

    /**
     * Get vehicle by using vin
     */
    @RequestMapping(value = "/car/{vin}", method = RequestMethod.GET)
    @ApiOperation(value = "Get car by vin")
    @ApiResponses(value = {@ApiResponse(code = 200, response = Vehicle.class, message = "Car response received"),
            @ApiResponse(code = 404, message = "No car found for given vin"), @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity<?> getCar(@PathVariable(value = "vin") String vin) {
        logger.info("Processing get car for the vin {}", vin);
        return ResponseEntity.ok(vehicleService.getVehicle(vin));
    }

    /**
     * Get polygon by using polygon Id
     */
    @RequestMapping(value = "/polygon/{polygonId}", method = RequestMethod.GET)
    @ApiOperation(value = "Get polygon by polygon id")
    @ApiResponses(value = {@ApiResponse(code = 200, response = Polygon.class, message = "Polygon response received"),
            @ApiResponse(code = 404, message = "No polygon found for given id"), @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity<?> getPolygon(@PathVariable(value = "polygonId") String polygonId) {
        logger.info("Processing get polygon for the id {}", polygonId);
        return ResponseEntity.ok(vehicleService.getPolygon(polygonId));
    }

    /**
     * Search vehicle and polygon
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ApiOperation(value = "Get polygon by polygon id, example: search?vin=xyz OR search?polygonId=yux")
    @ApiParam(name = "vin or polygon id", value = "vin of the car - Example: search?vin=xyz OR search?polygonId=yux", defaultValue = "")
    @ApiResponses(value = {@ApiResponse(code = 200, response = Polygon.class, message = "Polygon response received"),
            @ApiResponse(code = 200, response = Vehicle.class, message = "Car response received"),
            @ApiResponse(code = 404, message = "No polygon found for given id"),
            @ApiResponse(code = 500, message = "Internal error")})
    public ResponseEntity<?> getCarOrPolygon(@RequestParam(required = false) Map<String, String> _filter) {
        logger.info("Processing get polygon for the id {} and vin {}", _filter);
        return getResult(_filter);
    }

    private ResponseEntity<?> getResult(Map<String, String> _filter) {
        String[] keys = {"vin", "polygonId"};
        logger.info("Processing get polygon for the id {} and vin {}", _filter.keySet().stream().filter(p -> p.equals("vin") || p.equals("polygonId")).count());
        if (_filter.keySet().stream().filter(p -> p.equals("vin") || p.equals("polygonId")).count() <= 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid param entered, please use use param vin or polygonId");

        return _filter.containsKey(VIN)
                ? ResponseEntity.ok(vehicleService.getVehicle(_filter.get(VIN)))
                : ResponseEntity.ok(vehicleService.getPolygon(_filter.get(POLYGON_ID)));
    }
}
