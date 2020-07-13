package de.share_now.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@ToString
@RequiredArgsConstructor
public class GeoFeatures {
    private String name;

    private Geometry geometry;

}
