package me.whiteship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author whiteship
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Station {

    private String name;
    private List<Building> connectedBuildings;

    private boolean callout;

}
