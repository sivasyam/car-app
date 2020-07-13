package de.share_now.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Setter
@Getter
public class Result {
    private Vehicle vehicle;
    private Polygon polygon;
}
