package de.share_now.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Builder
@AllArgsConstructor
public class Tuple implements Serializable {
    private List<Vehicle> vehicles;
    private List<Polygon> polygons;
}
