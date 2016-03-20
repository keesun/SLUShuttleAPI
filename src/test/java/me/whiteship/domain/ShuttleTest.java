package me.whiteship.domain;

import me.whiteship.shuttle.ShuttleService;
import org.junit.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

/**
 * @author Keesun Baik
 */
public class ShuttleTest {

    private Station trb = ShuttleService.TRB;
    private Station blackfoot = ShuttleService.BLACKFOOT;
    private Station arizona = ShuttleService.ARIZONA;
    private Station day1North = ShuttleService.DAY_1_NORTH;

    private Shuttle route1AM = ShuttleService.ROUTE_1_AM;
    private Shuttle route1PM = ShuttleService.ROUTE_1_PM;

    @Test
    public void testParsingTime() {
        String time = "6:55 AM";
        LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("h:mm a")
                .withLocale(Locale.ENGLISH)); // without Locale an exception may occur.
        assertThat(localTime.getHour(), is(6));
        assertThat(localTime.getMinute(), is(55));
    }

    /**
     * The route 1's information is here:
     * http://www.slushuttle.com/routeschedules/Routes_oct_1_2015.pdf?dt=1457747483414
     */
    @Test
    public void testRoute1AM() {
        assertScheduleSize(route1AM, trb, 12);
        assertScheduleSize(route1AM, day1North, 11);
        assertScheduleSize(route1AM, blackfoot, 0);
        assertScheduleSize(route1AM, arizona, 0);

        assertTrue(route1AM.isAvailable(trb, blackfoot));
        assertTrue(route1AM.isAvailable(trb, arizona));
        assertTrue(route1AM.isAvailable(trb, day1North));
        // call-out station means drop only
        assertFalse(route1AM.isAvailable(blackfoot, arizona));
        assertFalse(route1AM.isAvailable(blackfoot, day1North));
        assertFalse(route1AM.isAvailable(blackfoot, trb));
        assertFalse(route1AM.isAvailable(arizona, day1North));
        assertFalse(route1AM.isAvailable(arizona, trb));
        assertFalse(route1AM.isAvailable(arizona, blackfoot));
        // support circular schedule.
        assertTrue(route1AM.isAvailable(day1North, trb));
        assertTrue(route1AM.isAvailable(day1North, blackfoot));
        assertTrue(route1AM.isAvailable(day1North, arizona));

        List<Schedule> fromTrbToDay1NortAt6am = route1AM.getSchedules(trb, day1North, LocalTime.of(6, 0));
        assertEquals(fromTrbToDay1NortAt6am.size(), 12);
        assertSchedule(fromTrbToDay1NortAt6am.get(0), trb, day1North, 6, 55);
        assertSchedule(fromTrbToDay1NortAt6am.get(1), trb, day1North, 7, 35);
        assertSchedule(fromTrbToDay1NortAt6am.get(2), trb, day1North, 8, 15);
        assertSchedule(fromTrbToDay1NortAt6am.get(3), trb, day1North, 8, 55);
        assertSchedule(fromTrbToDay1NortAt6am.get(4), trb, day1North, 9, 35);
        assertSchedule(fromTrbToDay1NortAt6am.get(5), trb, day1North, 10, 15);
        assertSchedule(fromTrbToDay1NortAt6am.get(6), trb, day1North, 10, 50);
        assertSchedule(fromTrbToDay1NortAt6am.get(7), trb, day1North, 11, 25);
        assertSchedule(fromTrbToDay1NortAt6am.get(8), trb, day1North, 12, 0);
        assertSchedule(fromTrbToDay1NortAt6am.get(9), trb, day1North, 12, 45);
        assertSchedule(fromTrbToDay1NortAt6am.get(10), trb, day1North, 13, 20);
        assertSchedule(fromTrbToDay1NortAt6am.get(11), trb, day1North, 13, 50);

        List<Schedule> fromTrbToBlackfootAt6am = route1AM.getSchedules(trb, blackfoot, LocalTime.of(6, 0));
        assertEquals(fromTrbToBlackfootAt6am.size(), 12);
        assertSchedule(fromTrbToBlackfootAt6am.get(0), trb, blackfoot, 6, 55);
        assertSchedule(fromTrbToBlackfootAt6am.get(1), trb, blackfoot, 7, 35);
        assertSchedule(fromTrbToBlackfootAt6am.get(2), trb, blackfoot, 8, 15);
        assertSchedule(fromTrbToBlackfootAt6am.get(3), trb, blackfoot, 8, 55);
        assertSchedule(fromTrbToBlackfootAt6am.get(4), trb, blackfoot, 9, 35);
        assertSchedule(fromTrbToBlackfootAt6am.get(5), trb, blackfoot, 10, 15);
        assertSchedule(fromTrbToBlackfootAt6am.get(6), trb, blackfoot, 10, 50);
        assertSchedule(fromTrbToBlackfootAt6am.get(7), trb, blackfoot, 11, 25);
        assertSchedule(fromTrbToBlackfootAt6am.get(8), trb, blackfoot, 12, 0);
        assertSchedule(fromTrbToBlackfootAt6am.get(9), trb, blackfoot, 12, 45);
        assertSchedule(fromTrbToBlackfootAt6am.get(10), trb, blackfoot, 13, 20);
        assertSchedule(fromTrbToBlackfootAt6am.get(11), trb, blackfoot, 13, 50);

        List<Schedule> fromTrbToDay1NorthAt10am = route1AM.getSchedules(trb, day1North, LocalTime.of(10, 0));
        assertEquals(fromTrbToDay1NorthAt10am.size(), 7);
        assertSchedule(fromTrbToDay1NorthAt10am.get(0), trb, day1North, 10, 15);
        assertSchedule(fromTrbToDay1NorthAt10am.get(1), trb, day1North, 10, 50);
        assertSchedule(fromTrbToDay1NorthAt10am.get(2), trb, day1North, 11, 25);
        assertSchedule(fromTrbToDay1NorthAt10am.get(3), trb, day1North, 12, 0);
        assertSchedule(fromTrbToDay1NorthAt10am.get(4), trb, day1North, 12, 45);
        assertSchedule(fromTrbToDay1NorthAt10am.get(5), trb, day1North, 13, 20);
        assertSchedule(fromTrbToDay1NorthAt10am.get(6), trb, day1North, 13, 50);


        List<Schedule> fromDay1NorthToTrbAt10am = route1AM.getSchedules(day1North, trb, LocalTime.of(10, 0));
        assertEquals(fromDay1NorthToTrbAt10am.size(), 6);
        assertSchedule(fromDay1NorthToTrbAt10am.get(0), day1North, trb, 10, 30);
        assertSchedule(fromDay1NorthToTrbAt10am.get(1), day1North, trb, 11, 5);
        assertSchedule(fromDay1NorthToTrbAt10am.get(2), day1North, trb, 11, 40);
        assertSchedule(fromDay1NorthToTrbAt10am.get(3), day1North, trb, 12, 15);
        assertSchedule(fromDay1NorthToTrbAt10am.get(4), day1North, trb, 13, 0);
        assertSchedule(fromDay1NorthToTrbAt10am.get(5), day1North, trb, 13, 35);
    }

    private void assertSchedule(Schedule schedule, Station departingStation, Station arrivingStation, int hour, int minutes) {
        assertEquals(schedule.getDepartingStation(), departingStation);
        assertEquals(schedule.getArrivingStation(), arrivingStation);
        assertEquals(schedule.getDepartingTime().getHour(), hour);
        assertEquals(schedule.getDepartingTime().getMinute(), minutes);
    }

    @Test
    public void testRoute1PM() {
        assertScheduleSize(route1PM, day1North, 7);
        assertScheduleSize(route1PM, trb, 6);
        assertScheduleSize(route1PM, blackfoot, 6);

        assertTrue(route1PM.isAvailable(day1North, trb));
        assertTrue(route1PM.isAvailable(day1North, blackfoot));
        assertTrue(route1PM.isAvailable(trb, blackfoot));
        assertTrue(route1PM.isAvailable(trb, day1North));
        assertTrue(route1PM.isAvailable(blackfoot, day1North));
        assertTrue(route1PM.isAvailable(blackfoot, trb));

        List<Schedule> fromDay1NorthToTrbAt230 = route1PM.getSchedules(day1North, trb, LocalTime.of(14, 30));
        assertEquals(fromDay1NorthToTrbAt230.size(), 6);
        assertSchedule(fromDay1NorthToTrbAt230.get(0), day1North, trb, 14, 45);
        assertSchedule(fromDay1NorthToTrbAt230.get(1), day1North, trb, 15, 25);
        assertSchedule(fromDay1NorthToTrbAt230.get(2), day1North, trb, 16, 15);
        assertSchedule(fromDay1NorthToTrbAt230.get(3), day1North, trb, 17, 15);
        assertSchedule(fromDay1NorthToTrbAt230.get(4), day1North, trb, 18, 5);
        assertSchedule(fromDay1NorthToTrbAt230.get(5), day1North, trb, 18, 55);

        List<Schedule> fromTrbToBlackfootAt435 = route1PM.getSchedules(trb, blackfoot, LocalTime.of(16, 35));
        assertEquals(fromTrbToBlackfootAt435.size(), 2);
        assertSchedule(fromTrbToBlackfootAt435.get(0), trb, blackfoot, 17, 35);
        assertSchedule(fromTrbToBlackfootAt435.get(1), trb, blackfoot, 18, 25);
    }

    private void assertScheduleSize(Shuttle shuttle, Station station, int count) {
        if (count == 0) {
            assertThat(shuttle.getSchedules().get(station), is(nullValue()));
        } else {
            assertThat(shuttle.getSchedules().get(station).size(), is(count));
        }
    }

}