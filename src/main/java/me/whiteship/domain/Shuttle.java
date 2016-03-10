package me.whiteship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author whiteship
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Shuttle {

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("h:mm a").withLocale(Locale.ENGLISH);

    private int number;
    private String description;
    private Station[] stations;
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

    public boolean available(Station departingStation, Station arrivingStation) {
        if (departingStation.isCallout()) {
            return false;
        }

        Station firstStation = stations[0];
        Station lastStation = stations[stations.length - 1];
        if (isEndToEnd(departingStation, arrivingStation, firstStation, lastStation)) {
            return true;
        }

        List<Station> stationList = Arrays.asList(this.stations);
        if (stationList.indexOf(departingStation) < stationList.indexOf(arrivingStation)) {
            return true;
        }

        return false;
    }

    private boolean isEndToEnd(Station departingStation, Station arrivingStation, Station firstStation, Station lastStation) {
        return (departingStation.equals(lastStation) && arrivingStation.equals(firstStation)) ||
                (departingStation.equals(firstStation) && arrivingStation.equals(lastStation));
    }
}
