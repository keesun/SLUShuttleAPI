package me.whiteship.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Keesun Baik
 */
@Data
public class ShuttleDTOs {

    @Data
    public static class ForScheduleResult {
        private int number;
    }

    @Data
    public static class ForShuttleResult {
        private int number;
        private String description;
        private List<StationDto> stations;
        private Map<StationDto, List<String>> schedules;
    }



}
