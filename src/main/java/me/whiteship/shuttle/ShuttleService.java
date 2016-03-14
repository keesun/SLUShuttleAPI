package me.whiteship.shuttle;

import me.whiteship.domain.Station;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Keesun Baik
 */
@Service
public class ShuttleService {

    private List<Station> stations;

    @PostConstruct
    public void initData() {
        this.stations = new ArrayList<>();
        stations.add(Station.builder().name("Day1North").build());
        stations.add(Station.builder().name("Blackfoot").build());
        stations.add(Station.builder().name("TRB").build());
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
}
