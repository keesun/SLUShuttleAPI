package me.whiteship.web;

import me.whiteship.domain.Station;
import me.whiteship.shuttle.ShuttleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author whiteship
 */
@Controller
public class FindShuttleController {

    @Autowired
    ShuttleService shuttleService;

    @RequestMapping(method = RequestMethod.GET, value = "/from/{from}/to/{to}/")
    public ResponseEntity find(@PathVariable String from, @PathVariable String to) {
        Station fromStation = shuttleService.findStationByName(from);
        Station toStation = shuttleService.findStationByName(to);
        return null;
    }
}
