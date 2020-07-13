package de.share_now.model;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
@Setter
@Getter
@RequiredArgsConstructor
public class Geometry3 {
    private List<List<List<Double>>> coordinates;

    private String type;

}
