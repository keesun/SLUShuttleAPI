package me.whiteship.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Keesun Baik
 */
@Data
public class Result {

    /**
     * Raw data that contains all available schedules
     */
    Map<Shuttle, List<Schedule>> schedules;

    /**
     * Available shuttles, same as the key set of the raw data.
     */
    List<Shuttle> shuttles;

    Shuttle recommendedShuttle;

    Schedule recommendedSchedule;

}
