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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    public void findSchedules() {
        Station trb = shuttleService.findStationByName("trb");
        Station blackfoot = shuttleService.findStationByName("blackfoot");
        Station arizona = shuttleService.findStationByName("arizona");

        Map<Shuttle, List<Schedule>> fromTrbToBlackfoot = shuttleService.findSchedules(trb, blackfoot, LocalTime.of(9, 35));
        assertEquals(fromTrbToBlackfoot.size(), 2);
        List<Schedule> route1AMSchedules = fromTrbToBlackfoot.get(ShuttleService.ROUTE_1_AM);
        assertEquals(route1AMSchedules.size(), 7);
        Schedule firstScheduleOfRoute1AM = route1AMSchedules.get(0);
        assertEquals(firstScheduleOfRoute1AM.getDepartingStation(), ShuttleService.TRB);
        assertEquals(firstScheduleOfRoute1AM.getArrivingStation(), ShuttleService.BLACKFOOT);
        assertEquals(firstScheduleOfRoute1AM.getDepartingTime().getHour(), 10);
        assertEquals(firstScheduleOfRoute1AM.getDepartingTime().getMinute(), 15);
        Schedule lastScheduleOfRoute1AM = route1AMSchedules.get(6);
        assertEquals(lastScheduleOfRoute1AM.getDepartingStation(), ShuttleService.TRB);
        assertEquals(lastScheduleOfRoute1AM.getArrivingStation(), ShuttleService.BLACKFOOT);
        assertEquals(lastScheduleOfRoute1AM.getDepartingTime().getHour(), 13);
        assertEquals(lastScheduleOfRoute1AM.getDepartingTime().getMinute(), 50);

        List<Schedule> route1PMSchedules = fromTrbToBlackfoot.get(ShuttleService.ROUTE_1_PM);
        assertEquals(route1PMSchedules.size(), 6);
        Schedule firstScheduleOfRounte1PM = route1PMSchedules.get(0);
        assertEquals(firstScheduleOfRounte1PM.getDepartingStation(), ShuttleService.TRB);
        assertEquals(firstScheduleOfRounte1PM.getArrivingStation(), ShuttleService.BLACKFOOT);
        assertEquals(firstScheduleOfRounte1PM.getDepartingTime().getHour(), 14);
        assertEquals(firstScheduleOfRounte1PM.getDepartingTime().getMinute(), 25);
        Schedule lastScheduleOfRoute1PM = route1PMSchedules.get(5);
        assertEquals(lastScheduleOfRoute1PM.getDepartingStation(), ShuttleService.TRB);
        assertEquals(lastScheduleOfRoute1PM.getArrivingStation(), ShuttleService.BLACKFOOT);
        assertEquals(lastScheduleOfRoute1PM.getDepartingTime().getHour(), 18);
        assertEquals(lastScheduleOfRoute1PM.getDepartingTime().getMinute(), 25);

        Map<Shuttle, List<Schedule>> fromTrbToArizona = shuttleService.findSchedules(trb, arizona, LocalTime.of(9, 35));
        assertEquals(fromTrbToArizona.size(), 1);

        // TODO D1N 에서 BlackFoot으로 가는게 6개만 나와야 하는데 아마 7개가 나올꺼다
        // TODO 그거 고치려면 call-out이랑 Drop-only 스케줄도 채워야 한다.
    }

}