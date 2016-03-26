package me.whiteship.web;

import me.whiteship.domain.Schedule;
import me.whiteship.domain.Shuttle;
import me.whiteship.domain.Station;
import me.whiteship.shuttle.NotFoundStationException;
import me.whiteship.shuttle.ShuttleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * @author whiteship
 */
@RestController
public class ShuttleController {

    @Autowired
    ShuttleService shuttleService;

    @RequestMapping(method = RequestMethod.GET, value = "/from/{from}/to/{to}")
    public ResponseEntity find(@PathVariable String from, @PathVariable String to) {
        Station fromStation = shuttleService.findStationByName(from);
        Station toStation = shuttleService.findStationByName(to);
        Map<Shuttle, List<Schedule>> schedules = shuttleService.findSchedules(fromStation, toStation, LocalTime.now());
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    @ExceptionHandler(NotFoundStationException.class)
    public ResponseEntity handleNotFoundStationException(NotFoundStationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
