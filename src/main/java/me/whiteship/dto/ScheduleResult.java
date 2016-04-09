package me.whiteship.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Keesun Baik
 */
@Data
public class ScheduleResult {

    /**
     * Raw data that contains all available schedules
     */
    Map<ShuttleDTOs.ForScheduleResult, List<ScheduleDto>> schedules;

    ShuttleDTOs.ForScheduleResult recommendedShuttle;

    ScheduleDto recommendedSchedule;

    StationDto departingStation;

    StationDto arrivingStation;

}
