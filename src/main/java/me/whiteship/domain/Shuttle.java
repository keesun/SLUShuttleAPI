package me.whiteship.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author whiteship
 */
@Data
@Builder
public class Shuttle {

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("h:mm a").withLocale(Locale.ENGLISH);

    private int number;
    private String description;
    private Station[] stations;
    private Boolean[] callouts;
    private Map<Station, List<LocalTime>> schedules;

    public void addSchedules(Station station, String... times) {
        if (this.schedules == null) {
            schedules = new HashMap<>();
        }

        List<LocalTime> localTimes = Arrays.asList(times).stream()
                .map(time -> LocalTime.parse(time, TIME_FORMATTER))
                .collect(Collectors.toList());
        schedules.put(station, localTimes);
    }

    /**
     * True only if when a user can ride a shuttle on the {@code departingStation} and get to the {@code arrivingStation}
     * with this shuttle.
     *
     * @param departingStation
     * @param arrivingStation
     * @return true means a user can use this shuttle otherwise return false.
     */
    public boolean isAvailable(Station departingStation, Station arrivingStation) {
        int stationIndex = Arrays.asList(this.stations).indexOf(departingStation);
        if (callouts[stationIndex]) {
            return false;
        }

        List<Station> stationList = Arrays.asList(this.stations);
        return stationList.contains(departingStation) && stationList.contains(arrivingStation);

    }

    private boolean isEndToEnd(Station departingStation, Station arrivingStation, Station firstStation, Station lastStation) {
        return (departingStation.equals(lastStation) && arrivingStation.equals(firstStation)) ||
                (departingStation.equals(firstStation) && arrivingStation.equals(lastStation));
    }
}
