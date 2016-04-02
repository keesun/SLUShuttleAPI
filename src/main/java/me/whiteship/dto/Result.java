package me.whiteship.dto;

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
    Map<ShuttleDto, List<ScheduleDto>> schedules;

    ShuttleDto recommendedShuttle;

    ScheduleDto recommendedSchedule;

    StationDto departingStation;

    StationDto arrivingStation;

}
