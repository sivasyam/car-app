package de.share_now.model;

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
public class Geometry {
    private String[] coordinates;

    private String type;

}
