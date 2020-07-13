package de.share_now.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Vehicle {
    private String polygonId;
    private float id;
    private float locationId;
    private String vin;
    private String numberPlate;
    Position position;
    private float fuel;
    private String model;
}
