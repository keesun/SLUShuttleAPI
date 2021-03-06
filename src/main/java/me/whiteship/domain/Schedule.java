package me.whiteship.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;

/**
 * @author whiteship
 */
@Data
@Builder
public class Schedule {

    private LocalTime departingTime;
    private Station departingStation;
    private LocalTime arrivingTime;
    private Station arrivingStation;

    /**
     * A -> B = 1
     * A -> B -> C = 2
     * A -> Callout -> Callout -> B = 1+
     * A -> Droponly -> B = 1+
     * A -> Callout = 1
     * A -> B -> Droponly = 2
     */
    private int numberOfStops;

    /**
     * The call-out station means that if you don't ask driver of the shuttle to stop this station,
     * the bus will just pass it by. You should tell the driver that you are going to this station.
     */
    private boolean callout;

    /**
     * Drop only station means you can get off the shuttle but you can't get on the shuttle on this station.
     */
    private boolean dropOnly;

}
