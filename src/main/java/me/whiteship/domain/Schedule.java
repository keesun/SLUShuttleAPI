package me.whiteship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * @author whiteship
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    private Date departingTime;
    private Station departingStation;
    private Date arrivingTime;
    private Station arrivingStation;

}
