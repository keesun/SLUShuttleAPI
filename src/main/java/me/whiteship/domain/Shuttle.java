package me.whiteship.domain;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * @author whiteship
 */
@Data
public class Shuttle {

    private int number;

    private List<Schedule> schedules;

    private LinkedList<Station> stations;

}
