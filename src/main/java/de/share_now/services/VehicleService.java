package de.share_now.services;

import de.share_now.model.Polygon;
import de.share_now.model.Tuple;
import de.share_now.model.Vehicle;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface VehicleService {

    void refreshCache() throws InterruptedException;

    Tuple fetchCars();
    Tuple fetchAllCars();

    Vehicle getVehicle(String vin);

    Polygon getPolygon(String polygonId);

    Tuple getCarOrPolygon(Map<String, String> _filter);
}
