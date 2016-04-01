package me.whiteship.shuttle;

import me.whiteship.Application;
import me.whiteship.domain.Schedule;
import me.whiteship.domain.Shuttle;
import me.whiteship.domain.Station;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Keesun Baik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class ShuttleServiceTest {

    @Autowired ShuttleService shuttleService;

    @Test
    public void di() {
        assertNotNull(shuttleService);
    }

    @Test
    public void findStationByName() {
        Station station = shuttleService.findStationByName("blackfoot");
        assertNotNull(station);
        assertEquals(station.getName().toLowerCase(), "blackfoot");
    }

    @Test(expected = NotFoundStationException.class)
    public void notFoundStationException() {
        shuttleService.findStationByName("not found");
    }

    @Test
    public void testFromTrbToBlackfoot() {
        Map<Shuttle, List<Schedule>> fromTrbToBlackfoot = shuttleService.findSchedules(Station.TRB, Station.BLACKFOOT, LocalTime.of(9, 35));
        assertEquals(fromTrbToBlackfoot.size(), 2);
        List<Schedule> route1AMSchedules = fromTrbToBlackfoot.get(Shuttle.ROUTE_1_AM);
        assertEquals(route1AMSchedules.size(), 7);

        Schedule firstScheduleOfRoute1AM = route1AMSchedules.get(0);
        assertEquals(firstScheduleOfRoute1AM.getDepartingStation(), Station.TRB);
        assertEquals(firstScheduleOfRoute1AM.getArrivingStation(), Station.BLACKFOOT);
        assertEquals(firstScheduleOfRoute1AM.getDepartingTime().getHour(), 10);
        assertEquals(firstScheduleOfRoute1AM.getDepartingTime().getMinute(), 15);
        assertEquals(firstScheduleOfRoute1AM.getNumberOfStops(), 1);

        Schedule lastScheduleOfRoute1AM = route1AMSchedules.get(6);
        assertEquals(lastScheduleOfRoute1AM.getDepartingStation(), Station.TRB);
        assertEquals(lastScheduleOfRoute1AM.getArrivingStation(), Station.BLACKFOOT);
        assertEquals(lastScheduleOfRoute1AM.getDepartingTime().getHour(), 13);
        assertEquals(lastScheduleOfRoute1AM.getDepartingTime().getMinute(), 50);
        assertEquals(lastScheduleOfRoute1AM.getNumberOfStops(), 1);

        List<Schedule> route1PMSchedules = fromTrbToBlackfoot.get(Shuttle.ROUTE_1_PM);
        assertEquals(route1PMSchedules.size(), 6);
        Schedule firstScheduleOfRounte1PM = route1PMSchedules.get(0);
        assertEquals(firstScheduleOfRounte1PM.getDepartingStation(), Station.TRB);
        assertEquals(firstScheduleOfRounte1PM.getArrivingStation(), Station.BLACKFOOT);
        assertEquals(firstScheduleOfRounte1PM.getDepartingTime().getHour(), 14);
        assertEquals(firstScheduleOfRounte1PM.getDepartingTime().getMinute(), 25);
        assertEquals(firstScheduleOfRounte1PM.getNumberOfStops(), 1);

        Schedule lastScheduleOfRoute1PM = route1PMSchedules.get(5);
        assertEquals(lastScheduleOfRoute1PM.getDepartingStation(), Station.TRB);
        assertEquals(lastScheduleOfRoute1PM.getArrivingStation(), Station.BLACKFOOT);
        assertEquals(lastScheduleOfRoute1PM.getDepartingTime().getHour(), 18);
        assertEquals(lastScheduleOfRoute1PM.getDepartingTime().getMinute(), 25);
        assertEquals(lastScheduleOfRoute1PM.getNumberOfStops(), 1);
    }

    @Test
    public void testFromDay1NorthToTRB() {
        Map<Shuttle, List<Schedule>> fromDay1NorthToTRB = shuttleService.findSchedules(Station.DAY_1_NORTH, Station.TRB, LocalTime.of(14, 00));
        assertEquals(fromDay1NorthToTRB.size(), 1);
        List<Schedule> route1PMSchedules = fromDay1NorthToTRB.get(Shuttle.ROUTE_1_PM);
        assertEquals(route1PMSchedules.size(), 7);
        Schedule lastSchedule = route1PMSchedules.get(6);
        assertEquals(lastSchedule.getDepartingStation(), Station.DAY_1_NORTH);
        assertEquals(lastSchedule.getDepartingTime().getHour(), 18);
        assertEquals(lastSchedule.getDepartingTime().getMinute(), 55);
        assertTrue(lastSchedule.isDropOnly());
        assertEquals(lastSchedule.getNumberOfStops(), 1);
    }

    @Test
    public void testFromBlackfootToTrb() {
        Map<Shuttle, List<Schedule>> fromBlackfootToTrb = shuttleService.findSchedules(Station.BLACKFOOT, Station.TRB, LocalTime.of(14, 0));
        assertEquals(fromBlackfootToTrb.size(), 1);
        List<Schedule> schedules = fromBlackfootToTrb.get(Shuttle.ROUTE_1_PM);
        assertEquals(schedules.size(), 6);

        Schedule firstSchedule = schedules.get(0);
        assertEquals(firstSchedule.getDepartingStation(), Station.BLACKFOOT);
        assertEquals(firstSchedule.getArrivingStation(), Station.TRB);
        assertEquals(firstSchedule.getDepartingTime().getHour(), 14);
        assertEquals(firstSchedule.getDepartingTime().getMinute(), 40);
        assertEquals(firstSchedule.getArrivingTime().getHour(), 15);
        assertEquals(firstSchedule.getArrivingTime().getMinute(), 5);
        assertFalse(firstSchedule.isDropOnly());
        assertFalse(firstSchedule.isCallout());
        assertEquals(firstSchedule.getNumberOfStops(), 2);
    }

}