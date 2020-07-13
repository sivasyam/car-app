package de.share_now.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.share_now.Application;
import de.share_now.model.Polygon;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import lombok.extern.slf4j.Slf4j;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class VehicleServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    public static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetVehicleByVin() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/car/2FTPX2763X6XAL182")
                .param("some-random", "4"))
                .andExpect(status().is(404));
    }

    @Test
    public void testGetPolygonById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/polygon/1D5HU18276R842SM3")
                .param("some-random", "4"))
                .andExpect(status().is(404));
    }

    @Test
    public void testSearchEndpoint() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/search")
                .param("polygonId", "58a58bf6766d51540f779926"));
    }

    @Test
    public void testCarsSearchEndpoint() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/search")
                .param("polygonId", "58a58bf6766d51540f779926"))
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        if (StringUtils.isNotEmpty(contentAsString)) {
            Polygon polygon = objectMapper.readValue(contentAsString, Polygon.class);
            String vin = (String) polygon.getVin().stream().findFirst().get();
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/search")
                    .param("vin", vin));
        }
    }
}
