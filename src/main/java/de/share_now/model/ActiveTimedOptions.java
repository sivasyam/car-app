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
public class ActiveTimedOptions {
    private String revenue;

    private String walking_range2;

    private String min;

    private String walking_range1;

    private String max;

    private String idle_time;

}
