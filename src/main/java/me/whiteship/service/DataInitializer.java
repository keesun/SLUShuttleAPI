package me.whiteship.service;

import me.whiteship.domain.*;
import me.whiteship.repository.BuildingRepository;
import me.whiteship.repository.ShuttleRepository;
import me.whiteship.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

        Calendar departingCal = Calendar.getInstance();
        departingCal.set(Calendar.HOUR_OF_DAY, 10);
        departingCal.set(Calendar.MINUTE, 0);
        departingCal.set(Calendar.SECOND, 0);
        departingCal.set(Calendar.MILLISECOND, 0);
        Date departingTime = departingCal.getTime();

        Calendar arrivingCal = Calendar.getInstance();
        arrivingCal.set(Calendar.HOUR_OF_DAY, 10);
        arrivingCal.set(Calendar.MINUTE, 5);
        arrivingCal.set(Calendar.SECOND, 0);
        arrivingCal.set(Calendar.MILLISECOND, 0);
        Date arrivingTime = arrivingCal.getTime();

        Shuttle shuttle12 = Shuttle.builder()
                .number(12)
                .stations(Arrays.asList(day1NorthStation, blackfootStation))
                .schedules(Arrays.asList(Schedule.builder()
                        .departingStation(day1NorthStation)
                        .departingTime(departingTime)
                        .arrivingStation(blackfootStation)
                        .arrivingTime(arrivingTime).build()))
                .build();
        shuttleRepository.save(shuttle12);
    }

}
