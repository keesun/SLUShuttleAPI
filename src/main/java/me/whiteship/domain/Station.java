package me.whiteship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @author whiteship
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Station {

    public static final Station TRB;
    public static final Station DAY_1_NORTH;
    public static final Station BLACKFOOT;
    public static final Station ARIZONA;

    static {
        TRB = Station.builder().name("TRB").build();
        DAY_1_NORTH = Station.builder().name("Day1North")
                .nickNames(Collections.singletonList("d1n")) // TODO input SEA number
                .build();
        BLACKFOOT = Station.builder().name("Blackfoot").build();
        ARIZONA = Station.builder().name("Arizona").build();
    }

    private String name;
    private List<Building> connectedBuildings;
    private List<String> nickNames;

}
