package me.whiteship.domain;

import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.ManyToMany;
import java.util.List;

/**
 * @author whiteship
 */
@Data
public class Shuttle {

    private int id;

    private int number;

    @ElementCollection
    private List<Schedule> schedules;

    @ManyToMany
    private List<Station> stations;

}
