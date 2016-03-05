package me.whiteship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author whiteship
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
