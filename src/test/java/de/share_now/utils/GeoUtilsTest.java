package de.share_now.utils;

import de.share_now.model.Polygon;
import de.share_now.model.Vehicle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static de.share_now.utils.GeoUtils.buildGeoList;
import static de.share_now.utils.GeoUtils.getVehicleList;
import static de.share_now.utils.GeoUtils.getPolygonList;
import static org.junit.Assert.assertTrue;

@TestPropertySource(
        properties = {
                "share.now.car2go.Stuttgart.url:${VEHICLE_ENDPOINT:http://host.docker.internal:3000/vehicles/Stuttgart}",
                "share.now.car2go.status.url:${VEHICLE_ENDPOINT_status:http://host.docker.internal:3000/status}"
        }
)
@RunWith(SpringRunner.class)
public class GeoUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(GeoUtilsTest.class);

    @Value("${share.now.car2go.Stuttgart.url}")
    public String vehicleUrl;

    @Value("${share.now.car2go.status.url}")
    public String statusUrl;
    @Test
    public void testExecuteVehicleRest() {
        List<Vehicle> response = getVehicleList(vehicleUrl, statusUrl);
        assertTrue(response.size() > 0);
    }

    @Test(expected = HttpClientErrorException.class)
    public void testExecuteVehicleRestException_1() {
        List<Vehicle> response = getVehicleList(vehicleUrl+1, statusUrl);
    }

    @Test
    public void testGetPolygonList() {
        List<Polygon> response = getPolygonList();
        assertTrue(response.size() > 0);
    }

    @Test
    public void testBuildGeoList() {
        List<Vehicle> vehicleList = getVehicleList(vehicleUrl, statusUrl);
        List<Polygon> polygonList = getPolygonList();
        List<Polygon> polygons = buildGeoList(vehicleList, polygonList);
        assertTrue(polygons.size() >= 0);
    }
}
