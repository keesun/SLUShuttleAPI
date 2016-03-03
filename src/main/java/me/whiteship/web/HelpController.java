package me.whiteship.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author whiteship
 */
@Controller
public class HelpController {

    @RequestMapping("/")
    public ResponseEntity help() {
        /**
         * TODO show help messages like:
         * GET /from/Rufus/to/BlackFoot/(now)
         * : You can find the next schedule for shuttle that depart around Rufus and arrive around BlackFoot
         * GET /from/Rufus/to/Doppler/around/4
         * : You can find schedules for shuttles that depart around Rufus and arrive around Doppler between 3:30 to 4:30.
         * GET /from/BigFoot/to/Doppler/until/4:30
         * : You can see schedules for shuttles that depart around BigFoot and arrive around Doppler until 4:30
         *
         * GET /shuttle/12
         * : Show current location and next stations of shuttle number 12.
         */
        return null;
    }

}
