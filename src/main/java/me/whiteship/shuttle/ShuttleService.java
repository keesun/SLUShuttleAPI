package me.whiteship.shuttle;

import me.whiteship.domain.Schedule;
import me.whiteship.domain.Shuttle;
import me.whiteship.domain.Station;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.*;

/**
 * @author Keesun Baik
 */
@Service
public class ShuttleService {

    private List<Station> stations;
    private List<Shuttle> shuttles;

    @PostConstruct
    public void initData() {
        this.stations = new ArrayList<>();
        final Station trb = Station.builder().name("TRB").build();
        stations.add(trb);

        final Station day1North = Station.builder().name("Day1North").build();
        stations.add(day1North);

        final Station blackfoot = Station.builder().name("Blackfoot").build();
        stations.add(blackfoot);

        final Station arizona = Station.builder().name("Arizona").build();
        stations.add(arizona);

        this.shuttles = new ArrayList<>();
        final Shuttle route1AM = Shuttle.builder()
                .number(1)
                .description("TRB - Blackfoot - Day 1 North \n" +
                        "AM Blackfoot & Arizona call-outs")
                .stations(new Station[]{trb, blackfoot, arizona, day1North})
                .callouts(new Boolean[]{false, true, true, false})
                .build();
        route1AM.addSchedules(trb, "6:55 AM", "7:35 AM", "8:15 AM", "8:55 AM", "9:35 AM", "10:15 AM", "10:50 AM",
                "11:25 AM", "12:00 PM", "12:45 PM", "1:20 PM", "1:50 PM");
        route1AM.addSchedules(day1North, "7:15 AM", "8:00 AM", "8:40 AM", "9:20 AM", "9:55 AM", "10:30 AM",
                "11:05 AM", "11:40 AM", "12:15 PM", "1:00 PM", "1:35 PM");
        shuttles.add(route1AM);

        final Shuttle route1PM = Shuttle.builder()
                .number(1)
                .description("PM: Day 1 North - TRB - Balckfoot")
                .stations(new Station[]{day1North, trb, blackfoot})
                .callouts(new Boolean[]{false, false, false})
                .build();
        route1PM.addSchedules(day1North, "2:10 PM", "2:45 PM", "3:25 PM", "4:15 PM", "5:15 PM", "6:05 PM", "6:55 PM");
        route1PM.addSchedules(trb, "2:25 PM", "3:05 PM", "3:45 PM", "4:35 PM", "5:35 PM", "6:25 PM");
        route1PM.addSchedules(blackfoot, "2:40 PM", "3:20 PM", "4:05 PM", "5:05 PM", "5:55 PM", "6:45 PM");
        shuttles.add(route1PM);
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
