package me.whiteship.dto;

import lombok.Data;

/**
 * @author Keesun Baik
 */
@Data
public class ScheduleDto {

    private LocalTimeDto departingTime;
    private LocalTimeDto arrivingTime;
    private int numberOfStops;
    private boolean dropOnly;
    private boolean callout;

}
