package me.whiteship.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author whiteship
 */
@Controller
public class FindShuttleController {

    @RequestMapping(method = RequestMethod.GET, value = "/from/{from}/to/{to}/{preposition}/{time}")
    public ResponseEntity find() {
        return null;
    }
}
