package me.whiteship.service;

import me.whiteship.domain.*;
import me.whiteship.repository.BuildingRepository;
import me.whiteship.repository.ShuttleRepository;
import me.whiteship.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Keesun Baik
 */
@Service
public class DataInitializer {

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    ShuttleRepository shuttleRepository;

    @Autowired
    StationRepository stationRepository;

    @PostConstruct
    public void initData() {
        Building rufus = Building.builder()
                .location(Location.builder().longitude(47.6235751f).latitude(-122.3389757f).build())
                .name("Rufus")
                .build();
        buildingRepository.save(rufus);

        Building day1North = Building.builder()
                .location(Location.builder().longitude(47.622839f).latitude(-122.3390443f).build())
                .name("Day 1 North")
                .build();
        buildingRepository.save(day1North);

        Building blackfoot = Building.builder()
                .location(Location.builder().longitude(47.6156899f).latitude(-122.3381664f).build())
                .name("Blackfoot")
                .build();
        buildingRepository.save(blackfoot);

        Station day1NorthStation = Station.builder()
                .name("Day 1 North")
                .connectedBuildings(Arrays.asList(day1North))
                .build();
        day1NorthStation = stationRepository.save(day1NorthStation);

        Station blackfootStation = Station.builder()
                .name("Blackfoot")
                .build();
        stationRepository.save(blackfootStation);

        Shuttle shuttle12 = Shuttle.builder()
                .number(12)
                .stations(Arrays.asList(day1NorthStation, blackfootStation))
                .schedules(Arrays.asList(
                        schedule(day1NorthStation, time(10, 0), blackfootStation, time(10, 5)),
                        schedule(blackfootStation, time(10, 5), day1NorthStation, time(10, 10)),
                        schedule(day1NorthStation, time(10, 10), blackfootStation, time(10, 15)),
                        schedule(blackfootStation, time(10, 15), day1NorthStation, time(10, 20))
                )).build();
        shuttleRepository.save(shuttle12);
    }

    private Date time(int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private Schedule schedule(Station departingStation, Date departingTime, Station arrivingStation, Date arrivingTime) {
        return Schedule.builder().departingStation(departingStation)
                .departingTime(departingTime)
                .arrivingStation(arrivingStation)
                .arrivingTime(arrivingTime)
                .build();
    }

}
