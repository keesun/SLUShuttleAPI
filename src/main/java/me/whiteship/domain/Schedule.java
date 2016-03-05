package me.whiteship.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author whiteship
 */
@Data
@Embeddable
public class Schedule {

    @Temporal(TemporalType.TIME)
    private Date departingTime;

    private Station departingStation;

    @Temporal(TemporalType.TIME)
    private Date arrivingTime;

    private Station arrivingStation;

}
