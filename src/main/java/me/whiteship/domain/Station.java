package me.whiteship.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author whiteship
 */
@Data
@Entity
public class Station {

    @Id @GeneratedValue
    private int id;

    private String name;

    @OneToMany
    private List<Building> connectedBuildings;
}
