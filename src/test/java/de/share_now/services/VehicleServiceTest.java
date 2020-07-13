package de.share_now.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VehicleServiceTest {

    @Mock
    VehicleService vehicleService;

    @Test
    public void testFetchCars() {
        vehicleService.fetchCars();
    }

}
