package me.whiteship.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author whiteship
 */
@Data
@Builder
public class Schedule {

    private Date departingTime;
    private Station departingStation;
    private Date arrivingTime;
    private Station arrivingStation;

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
