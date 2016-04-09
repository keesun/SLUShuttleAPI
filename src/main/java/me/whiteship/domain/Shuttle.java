package me.whiteship.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author whiteship
 */
@Data
@Builder
public class Shuttle {

    public static final Shuttle ROUTE_1_AM;
    public static final Shuttle ROUTE_1_PM;
    public static final LocalTime CALL_OUT = LocalTime.MAX;
    public static final LocalTime DROP_ONLY = LocalTime.MIN;

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("h:mm a").withLocale(Locale.ENGLISH);

    static {
        ROUTE_1_AM = Shuttle.builder()
                .number(1)
                .description("TRB - Blackfoot - Day 1 North \n" +
                        "AM Blackfoot & Arizona call-outs")
                .stations(new Station[]{Station.TRB, Station.BLACKFOOT, Station.ARIZONA, Station.DAY_1_NORTH})
                .build();
        ROUTE_1_AM.addSchedules(Station.TRB, "6:55 AM", "7:35 AM", "8:15 AM", "8:55 AM", "9:35 AM", "10:15 AM", "10:50 AM",
                "11:25 AM", "12:00 PM", "12:45 PM", "1:20 PM", "1:50 PM");
        ROUTE_1_AM.addSchedules(Station.BLACKFOOT, CALL_OUT, CALL_OUT, CALL_OUT, CALL_OUT, CALL_OUT, CALL_OUT, CALL_OUT,
                CALL_OUT, CALL_OUT, CALL_OUT, CALL_OUT, CALL_OUT);
        ROUTE_1_AM.addSchedules(Station.ARIZONA, CALL_OUT, CALL_OUT, CALL_OUT, CALL_OUT, CALL_OUT, CALL_OUT, CALL_OUT,
                CALL_OUT, CALL_OUT, CALL_OUT, CALL_OUT, CALL_OUT);
        ROUTE_1_AM.addSchedules(Station.DAY_1_NORTH, "7:15 AM", "8:00 AM", "8:40 AM", "9:20 AM", "9:55 AM", "10:30 AM",
                "11:05 AM", "11:40 AM", "12:15 PM", "1:00 PM", "1:35 PM");

        ROUTE_1_PM = Shuttle.builder()
                .number(1)
                .description("PM: Day 1 North - TRB - Balckfoot")
                .stations(new Station[]{Station.DAY_1_NORTH, Station.TRB, Station.BLACKFOOT})
                .build();
        ROUTE_1_PM.addSchedules(Station.DAY_1_NORTH, "2:10 PM", "2:45 PM", "3:25 PM", "4:15 PM", "5:15 PM", "6:05 PM", "6:55 PM");
        ROUTE_1_PM.addSchedules(Station.TRB, LocalTime.of(14, 25), LocalTime.of(15, 5), LocalTime.of(15, 45), LocalTime.of(16, 35),
                LocalTime.of(17, 35), LocalTime.of(18, 25), DROP_ONLY);
        ROUTE_1_PM.addSchedules(Station.BLACKFOOT, "2:40 PM", "3:20 PM", "4:05 PM", "5:05 PM", "5:55 PM", "6:45 PM");
    }

    private int number;
    private String description;
    private Station[] stations;
    private Map<Station, List<LocalTime>> schedules;

    private void addSchedules(Station station, String... times) {
        if (this.schedules == null) {
            schedules = new HashMap<>();
        }

        List<LocalTime> localTimes = Arrays.asList(times).stream()
                .map(time -> LocalTime.parse(time, TIME_FORMATTER))
                .collect(Collectors.toList());
        schedules.put(station, localTimes);
    }

    private void addSchedules(Station station, LocalTime... times) {
        if (this.schedules == null) {
            schedules = new HashMap<>();
        }

        schedules.put(station, Arrays.asList(times));
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
        List<Station> stationList = Arrays.asList(this.stations);
        boolean available = stationList.contains(departingStation) && stationList.contains(arrivingStation);

        List<LocalTime> departingTimes = getSchedules().get(departingStation);
        boolean departable = false;
        for (LocalTime time : departingTimes) {
            if (!time.equals(CALL_OUT) && !time.equals(DROP_ONLY)) {
                departable = true;
            }
        }

        return available && departable;
    }

    private boolean isEndToEnd(Station departingStation, Station arrivingStation, Station firstStation, Station lastStation) {
        return (departingStation.equals(lastStation) && arrivingStation.equals(firstStation)) ||
                (departingStation.equals(firstStation) && arrivingStation.equals(lastStation));
    }

    public List<Schedule> getSchedules(Station departingStation, Station arrivingStation, LocalTime localTime) {
        List<LocalTime> arrivingSchedules = getSchedules().get(arrivingStation);
        List<Station> stations = Arrays.asList(this.stations);
        int indexOfDepartingStation = stations.indexOf(departingStation);
        int indexOfArrivingStation = stations.indexOf(arrivingStation);
        boolean isRoundBack = indexOfArrivingStation < indexOfDepartingStation;

        AtomicInteger index = new AtomicInteger();
        return getSchedules().get(departingStation).stream()
                .filter(departingTime -> {
                    boolean isCallout = departingTime.equals(CALL_OUT);
                    boolean isDropOnly = departingTime.equals(DROP_ONLY);
                    int indexOfArrivingTime = isRoundBack ? index.get() + 1 : index.get();
                    boolean hasArrivingTime = arrivingSchedules.size() > indexOfArrivingTime;
                    index.incrementAndGet();
                    return !isCallout && !isDropOnly && departingTime.isAfter(localTime) && hasArrivingTime;
                })
                .map(departingTime -> {
                    assert arrivingSchedules != null;
                    int indexOfArrivingTIme = isRoundBack ? index.get() : index.get() - 1;
                    LocalTime arrivingTime = arrivingSchedules.get(indexOfArrivingTIme);
                    Schedule schedule = createSchedule(departingStation, arrivingStation, departingTime, arrivingTime);
                    setNUmberOfStops(stations, indexOfDepartingStation, indexOfArrivingStation, index, schedule);
                    return schedule;
                })
                .collect(Collectors.toList());
    }

    private Schedule createSchedule(Station departingStation, Station arrivingStation, LocalTime departingTime, LocalTime arrivingTime) {
        boolean isCallout = arrivingTime.equals(CALL_OUT);
        boolean isDropOnly = arrivingTime.equals(DROP_ONLY);
        Schedule schedule = Schedule.builder()
                .departingStation(departingStation)
                .departingTime(departingTime)
                .arrivingStation(arrivingStation)
                .callout(isCallout)
                .dropOnly(isCallout || isDropOnly)
                .build();
        if (!isCallout && !isDropOnly) {
            schedule.setArrivingTime(arrivingTime);
        }
        return schedule;
    }

    private void setNUmberOfStops(final List<Station> stations, int indexOfDepartingStation, int indexOfArrivingStation, final AtomicInteger index, final Schedule schedule) {
        int cursor = indexOfDepartingStation;
        int numberOfStations = 0;
        while (cursor != indexOfArrivingStation) {
            Station currentStation = stations.get(cursor);
            int indexOfCurrentStation = stations.indexOf(currentStation);
            int indexOfSchedule = indexOfCurrentStation < indexOfDepartingStation ? index.get() : index.get() - 1;
            LocalTime currentStationTime = getSchedules().get(currentStation).get(indexOfSchedule);
            if (!currentStationTime.equals(CALL_OUT) && !currentStationTime.equals(DROP_ONLY)) {
                numberOfStations++;
            }
            cursor = (cursor + 1 == stations.size()) ? 0 : cursor + 1;
        }
        schedule.setNumberOfStops(numberOfStations);
    }
}
