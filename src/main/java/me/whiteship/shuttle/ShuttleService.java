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

    public static final Station TRB;
    public static final Station DAY_1_NORTH;
    public static final Station BLACKFOOT;
    public static final Station ARIZONA;

    public static final Shuttle ROUTE_1_AM;
    public static final Shuttle ROUTE_1_PM;

    static {
        stations = new ArrayList<>();

        TRB = Station.builder().name("TRB").build();
        stations.add(TRB);

        DAY_1_NORTH = Station.builder().name("Day1North").build();
        stations.add(DAY_1_NORTH);

        BLACKFOOT = Station.builder().name("Blackfoot").build();
        stations.add(BLACKFOOT);

        ARIZONA = Station.builder().name("Arizona").build();
        stations.add(ARIZONA);


        shuttles = new ArrayList<>();
        ROUTE_1_AM = Shuttle.builder()
                .number(1)
                .description("TRB - Blackfoot - Day 1 North \n" +
                        "AM Blackfoot & Arizona call-outs")
                .stations(new Station[]{TRB, BLACKFOOT, ARIZONA, DAY_1_NORTH})
                .callouts(new Boolean[]{false, true, true, false})
                .build();
        ROUTE_1_AM.addSchedules(TRB, "6:55 AM", "7:35 AM", "8:15 AM", "8:55 AM", "9:35 AM", "10:15 AM", "10:50 AM",
                "11:25 AM", "12:00 PM", "12:45 PM", "1:20 PM", "1:50 PM");
        ROUTE_1_AM.addSchedules(DAY_1_NORTH, "7:15 AM", "8:00 AM", "8:40 AM", "9:20 AM", "9:55 AM", "10:30 AM",
                "11:05 AM", "11:40 AM", "12:15 PM", "1:00 PM", "1:35 PM");
        shuttles.add(ROUTE_1_AM);

        ROUTE_1_PM = Shuttle.builder()
                .number(1)
                .description("PM: Day 1 North - TRB - Balckfoot")
                .stations(new Station[]{DAY_1_NORTH, TRB, BLACKFOOT})
                .callouts(new Boolean[]{false, false, false})
                .build();
        ROUTE_1_PM.addSchedules(DAY_1_NORTH, "2:10 PM", "2:45 PM", "3:25 PM", "4:15 PM", "5:15 PM", "6:05 PM", "6:55 PM");
        ROUTE_1_PM.addSchedules(TRB, "2:25 PM", "3:05 PM", "3:45 PM", "4:35 PM", "5:35 PM", "6:25 PM");
        ROUTE_1_PM.addSchedules(BLACKFOOT, "2:40 PM", "3:20 PM", "4:05 PM", "5:05 PM", "5:55 PM", "6:45 PM");
        shuttles.add(ROUTE_1_PM);
    }

    public Station findStationByName(String stationName) {
        Optional<Station> first = stations.stream()
                .filter(station -> station.getName().toLowerCase().equals(stationName.toLowerCase()))
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
