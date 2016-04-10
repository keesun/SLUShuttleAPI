package me.whiteship.web;

import me.whiteship.domain.Schedule;
import me.whiteship.domain.Shuttle;
import me.whiteship.domain.Station;
import me.whiteship.dto.*;
import me.whiteship.shuttle.StationNotFoundException;
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

        ScheduleResult scheduleResult = new ScheduleResult();
        scheduleResult.setSchedules(getForScheduleResultListMap(schedules));
        scheduleResult.setDepartingStation(modelMapper.map(fromStation, StationDto.class));
        scheduleResult.setArrivingStation(modelMapper.map(toStation, StationDto.class));
        return new ResponseEntity<>(scheduleResult, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/shuttle/{number}")
    public ResponseEntity shuttle(@PathVariable int number) {
        List<Shuttle> shuttles = shuttleService.findShuttle(number);
        List<ShuttleDTOs.ForShuttleResult> result = shuttles.stream().map(shuttle -> {
            ShuttleDTOs.ForShuttleResult dto = modelMapper.map(shuttle, ShuttleDTOs.ForShuttleResult.class);
            dto.setSchedules(getStationDtoListMap(shuttle));
            return dto;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private Map<ShuttleDTOs.ForScheduleResult, List<ScheduleDto>> getForScheduleResultListMap(Map<Shuttle, List<Schedule>> schedules) {
        Map<ShuttleDTOs.ForScheduleResult, List<ScheduleDto>> schedulesDto = new HashMap<>();
        schedules.forEach((shuttle, scheduleList) -> schedulesDto.put(mapShuttleDto(shuttle), mapSchedules(scheduleList)));
        return schedulesDto;
    }

    private Map<StationDto, List<String>> getStationDtoListMap(Shuttle shuttle) {
        Map<StationDto, List<String>> schedulesDto = new HashMap<>();
        shuttle.getSchedules().forEach((station, scheduleList) -> schedulesDto.put(mapStationDto(station), mapLocalTimes(scheduleList)));
        return schedulesDto;
    }

    private List<String> mapLocalTimes(List<LocalTime> localTimes) {
        return localTimes.stream().map(lt -> {
            if (lt.equals(Shuttle.DROP_ONLY)) {
                return "Drop Only";
            }
            if (lt.equals(Shuttle.CALL_OUT)) {
                return "Call Out";
            }
            return lt.format(Shuttle.TIME_FORMATTER);
        }).collect(Collectors.toList());
    }

    private StationDto mapStationDto(Station station) {
        return modelMapper.map(station, StationDto.class);
    }

    private List<ScheduleDto> mapSchedules(List<Schedule> schedules) {
        return schedules.stream().map(s -> modelMapper.map(s, ScheduleDto.class)).collect(Collectors.toList());
    }

    private ShuttleDTOs.ForScheduleResult mapShuttleDto(Shuttle shuttle) {
        return modelMapper.map(shuttle, ShuttleDTOs.ForScheduleResult.class);
    }

    @ExceptionHandler(StationNotFoundException.class)
    public ResponseEntity handleStationNotFoundException(StationNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
