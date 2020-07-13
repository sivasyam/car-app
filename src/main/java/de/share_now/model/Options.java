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
public class Options {
    private String area;

    private String is_excluded;

    private String active;

}
