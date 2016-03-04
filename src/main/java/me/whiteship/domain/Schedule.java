package me.whiteship.domain;

import lombok.Data;

import java.time.LocalTime;

/**
 * @author whiteship
 */
@Data
public class Schedule {

    private LocalTime departingTime;

    private Station departingStation;

    private LocalTime arrivingTime;

    private Station arrivingStation;

}
