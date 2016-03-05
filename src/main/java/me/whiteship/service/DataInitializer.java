package me.whiteship.service;

import me.whiteship.domain.Building;
import me.whiteship.domain.Location;
import me.whiteship.repository.BuildingRepository;
import me.whiteship.repository.ShuttleRepository;
import me.whiteship.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

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
                .location(Location.builder().longitude(47.6249024f).latitude(47.6249024f).build())
                .name("Rufus")
                .build();
        buildingRepository.save(rufus);
    }

}
