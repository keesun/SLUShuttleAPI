package me.whiteship.domain;

import lombok.Data;

import java.util.List;

/**
 * @author whiteship
 */
@Data
public class Station {

    private String name;

    private List<Building> connectedBuildings;
}
