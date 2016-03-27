package me.whiteship.shuttle;

import me.whiteship.domain.Schedule;
import me.whiteship.domain.Shuttle;
import me.whiteship.domain.Station;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;

/**
 * @author Keesun Baik
 */
@Service
public class ShuttleService {

    private static final List<Station> stations;
    private static final List<Shuttle> shuttles;

    static {
        stations = new ArrayList<>();
        stations.add(Station.ARIZONA);
        stations.add(Station.BLACKFOOT);
        stations.add(Station.DAY_1_NORTH);
        stations.add(Station.TRB);

        shuttles = new ArrayList<>();
        shuttles.add(Shuttle.ROUTE_1_AM);
        shuttles.add(Shuttle.ROUTE_1_PM);
    }

    public Station findStationByName(String stationName) {
        Optional<Station> first = stations.stream()
                .filter(station -> {
                    boolean isSameName = station.getName().toLowerCase().equals(stationName.toLowerCase());
                    List<String> nickNames = station.getNickNames();
                    boolean isSameNickName = nickNames != null && nickNames.contains(stationName.toLowerCase());
                    return isSameName || isSameNickName;
                })
                .findFirst();
        if (first.isPresent()) {
            return first.get();
        } else {
            throw new NotFoundStationException(stationName);
        }
    }

    public Map<Shuttle, List<Schedule>> findSchedules(Station fromStation, Station toStation, LocalTime now) {
        Map<Shuttle, List<Schedule>> result = new HashMap<>();
        shuttles.stream().forEach(shuttle -> {
            if (shuttle.isAvailable(fromStation, toStation)) {
                List<Schedule> schedules = shuttle.getSchedules(fromStation, toStation, now);
                if (schedules.size() > 0) {
                    result.put(shuttle, schedules);
                }
            }
        });
        return result;
    }
}
