package me.whiteship.web;

import me.whiteship.domain.Schedule;
import me.whiteship.domain.Shuttle;
import me.whiteship.domain.Station;
import me.whiteship.dto.Result;
import me.whiteship.dto.ScheduleDto;
import me.whiteship.dto.ShuttleDto;
import me.whiteship.dto.StationDto;
import me.whiteship.shuttle.NotFoundStationException;
import me.whiteship.shuttle.ShuttleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author whiteship
 */
@RestController
public class ShuttleController {

    @Autowired
    ShuttleService shuttleService;

    @Autowired
    ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/from/{from}/to/{to}")
    public ResponseEntity find(@PathVariable String from, @PathVariable String to) {
        Station fromStation = shuttleService.findStationByName(from);
        Station toStation = shuttleService.findStationByName(to);
        Map<Shuttle, List<Schedule>> schedules = shuttleService.findSchedules(fromStation, toStation, LocalTime.now());

        Result result = new Result();
        Map<ShuttleDto, List<ScheduleDto>> schedulesDto = new HashMap<>();
        schedules.forEach((shuttle, scheduleList) -> schedulesDto.put(mapShuttleDto(shuttle), mapSchedules(scheduleList)));
        result.setSchedules(schedulesDto);
        result.setDepartingStation(modelMapper.map(fromStation, StationDto.class));
        result.setArrivingStation(modelMapper.map(toStation, StationDto.class));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/shuttle/{number}")
    public ResponseEntity shuttle(@PathVariable int number) {
        Shuttle shuttle = shuttleService.findShuttle(number);
        return null;
    }

    private List<ScheduleDto> mapSchedules(List<Schedule> schedules) {
        return schedules.stream().map(s -> modelMapper.map(s, ScheduleDto.class)).collect(Collectors.toList());
    }

    private ShuttleDto mapShuttleDto(Shuttle shuttle) {
        return modelMapper.map(shuttle, ShuttleDto.class);
    }

    @ExceptionHandler(NotFoundStationException.class)
    public ResponseEntity handleNotFoundStationException(NotFoundStationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
