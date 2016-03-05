package me.whiteship.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author whiteship
 */
@Data
@Entity
public class Shuttle {

    @Id
    @GeneratedValue
    private int id;

    private int number;

    @ElementCollection
    private List<Schedule> schedules;

    @ManyToMany
    private List<Station> stations;

}
