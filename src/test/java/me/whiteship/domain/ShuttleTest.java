package me.whiteship.domain;

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
        assertScheduleSize(Shuttle.ROUTE_1_AM, Station.TRB, 12);
        assertScheduleSize(Shuttle.ROUTE_1_AM, Station.DAY_1_NORTH, 11);
        assertScheduleSize(Shuttle.ROUTE_1_AM, Station.BLACKFOOT, 12);
        assertScheduleSize(Shuttle.ROUTE_1_AM, Station.ARIZONA, 12);

        assertTrue(Shuttle.ROUTE_1_AM.isAvailable(Station.TRB, Station.BLACKFOOT));
        assertTrue(Shuttle.ROUTE_1_AM.isAvailable(Station.TRB, Station.ARIZONA));
        assertTrue(Shuttle.ROUTE_1_AM.isAvailable(Station.TRB, Station.DAY_1_NORTH));
        // call-out station means drop only
        assertFalse(Shuttle.ROUTE_1_AM.isAvailable(Station.BLACKFOOT, Station.ARIZONA));
        assertFalse(Shuttle.ROUTE_1_AM.isAvailable(Station.BLACKFOOT, Station.DAY_1_NORTH));
        assertFalse(Shuttle.ROUTE_1_AM.isAvailable(Station.BLACKFOOT, Station.TRB));
        assertFalse(Shuttle.ROUTE_1_AM.isAvailable(Station.ARIZONA, Station.DAY_1_NORTH));
        assertFalse(Shuttle.ROUTE_1_AM.isAvailable(Station.ARIZONA, Station.TRB));
        assertFalse(Shuttle.ROUTE_1_AM.isAvailable(Station.ARIZONA, Station.BLACKFOOT));
        // support circular schedule.
        assertTrue(Shuttle.ROUTE_1_AM.isAvailable(Station.DAY_1_NORTH, Station.TRB));
        assertTrue(Shuttle.ROUTE_1_AM.isAvailable(Station.DAY_1_NORTH, Station.BLACKFOOT));
        assertTrue(Shuttle.ROUTE_1_AM.isAvailable(Station.DAY_1_NORTH, Station.ARIZONA));

        List<Schedule> fromTrbToDay1NortAt6am = Shuttle.ROUTE_1_AM.getSchedules(Station.TRB, Station.DAY_1_NORTH, LocalTime.of(6, 0));
        assertEquals(fromTrbToDay1NortAt6am.size(), 12);
        assertSchedule(fromTrbToDay1NortAt6am.get(0), Station.TRB, Station.DAY_1_NORTH, 6, 55);
        assertSchedule(fromTrbToDay1NortAt6am.get(1), Station.TRB, Station.DAY_1_NORTH, 7, 35);
        assertSchedule(fromTrbToDay1NortAt6am.get(2), Station.TRB, Station.DAY_1_NORTH, 8, 15);
        assertSchedule(fromTrbToDay1NortAt6am.get(3), Station.TRB, Station.DAY_1_NORTH, 8, 55);
        assertSchedule(fromTrbToDay1NortAt6am.get(4), Station.TRB, Station.DAY_1_NORTH, 9, 35);
        assertSchedule(fromTrbToDay1NortAt6am.get(5), Station.TRB, Station.DAY_1_NORTH, 10, 15);
        assertSchedule(fromTrbToDay1NortAt6am.get(6), Station.TRB, Station.DAY_1_NORTH, 10, 50);
        assertSchedule(fromTrbToDay1NortAt6am.get(7), Station.TRB, Station.DAY_1_NORTH, 11, 25);
        assertSchedule(fromTrbToDay1NortAt6am.get(8), Station.TRB, Station.DAY_1_NORTH, 12, 0);
        assertSchedule(fromTrbToDay1NortAt6am.get(9), Station.TRB, Station.DAY_1_NORTH, 12, 45);
        assertSchedule(fromTrbToDay1NortAt6am.get(10), Station.TRB, Station.DAY_1_NORTH, 13, 20);
        assertSchedule(fromTrbToDay1NortAt6am.get(11), Station.TRB, Station.DAY_1_NORTH, 13, 50);

        List<Schedule> fromTrbToBlackfootAt6am = Shuttle.ROUTE_1_AM.getSchedules(Station.TRB, Station.BLACKFOOT, LocalTime.of(6, 0));
        assertEquals(fromTrbToBlackfootAt6am.size(), 12);
        assertSchedule(fromTrbToBlackfootAt6am.get(0), Station.TRB, Station.BLACKFOOT, 6, 55);
        assertSchedule(fromTrbToBlackfootAt6am.get(1), Station.TRB, Station.BLACKFOOT, 7, 35);
        assertSchedule(fromTrbToBlackfootAt6am.get(2), Station.TRB, Station.BLACKFOOT, 8, 15);
        assertSchedule(fromTrbToBlackfootAt6am.get(3), Station.TRB, Station.BLACKFOOT, 8, 55);
        assertSchedule(fromTrbToBlackfootAt6am.get(4), Station.TRB, Station.BLACKFOOT, 9, 35);
        assertSchedule(fromTrbToBlackfootAt6am.get(5), Station.TRB, Station.BLACKFOOT, 10, 15);
        assertSchedule(fromTrbToBlackfootAt6am.get(6), Station.TRB, Station.BLACKFOOT, 10, 50);
        assertSchedule(fromTrbToBlackfootAt6am.get(7), Station.TRB, Station.BLACKFOOT, 11, 25);
        assertSchedule(fromTrbToBlackfootAt6am.get(8), Station.TRB, Station.BLACKFOOT, 12, 0);
        assertSchedule(fromTrbToBlackfootAt6am.get(9), Station.TRB, Station.BLACKFOOT, 12, 45);
        assertSchedule(fromTrbToBlackfootAt6am.get(10), Station.TRB, Station.BLACKFOOT, 13, 20);
        assertSchedule(fromTrbToBlackfootAt6am.get(11), Station.TRB, Station.BLACKFOOT, 13, 50);

        List<Schedule> fromTrbToDay1NorthAt10am = Shuttle.ROUTE_1_AM.getSchedules(Station.TRB, Station.DAY_1_NORTH, LocalTime.of(10, 0));
        assertEquals(fromTrbToDay1NorthAt10am.size(), 7);
        assertSchedule(fromTrbToDay1NorthAt10am.get(0), Station.TRB, Station.DAY_1_NORTH, 10, 15);
        assertSchedule(fromTrbToDay1NorthAt10am.get(1), Station.TRB, Station.DAY_1_NORTH, 10, 50);
        assertSchedule(fromTrbToDay1NorthAt10am.get(2), Station.TRB, Station.DAY_1_NORTH, 11, 25);
        assertSchedule(fromTrbToDay1NorthAt10am.get(3), Station.TRB, Station.DAY_1_NORTH, 12, 0);
        assertSchedule(fromTrbToDay1NorthAt10am.get(4), Station.TRB, Station.DAY_1_NORTH, 12, 45);
        assertSchedule(fromTrbToDay1NorthAt10am.get(5), Station.TRB, Station.DAY_1_NORTH, 13, 20);
        assertSchedule(fromTrbToDay1NorthAt10am.get(6), Station.TRB, Station.DAY_1_NORTH, 13, 50);


        List<Schedule> fromDay1NorthToTrbAt10am = Shuttle.ROUTE_1_AM.getSchedules(Station.DAY_1_NORTH, Station.TRB, LocalTime.of(10, 0));
        assertEquals(fromDay1NorthToTrbAt10am.size(), 6);
        assertSchedule(fromDay1NorthToTrbAt10am.get(0), Station.DAY_1_NORTH, Station.TRB, 10, 30);
        assertSchedule(fromDay1NorthToTrbAt10am.get(1), Station.DAY_1_NORTH, Station.TRB, 11, 5);
        assertSchedule(fromDay1NorthToTrbAt10am.get(2), Station.DAY_1_NORTH, Station.TRB, 11, 40);
        assertSchedule(fromDay1NorthToTrbAt10am.get(3), Station.DAY_1_NORTH, Station.TRB, 12, 15);
        assertSchedule(fromDay1NorthToTrbAt10am.get(4), Station.DAY_1_NORTH, Station.TRB, 13, 0);
        assertSchedule(fromDay1NorthToTrbAt10am.get(5), Station.DAY_1_NORTH, Station.TRB, 13, 35);
    }

    private void assertSchedule(Schedule schedule, Station departingStation, Station arrivingStation, int hour, int minutes) {
        assertEquals(schedule.getDepartingStation(), departingStation);
        assertEquals(schedule.getArrivingStation(), arrivingStation);
        assertEquals(schedule.getDepartingTime().getHour(), hour);
        assertEquals(schedule.getDepartingTime().getMinute(), minutes);
    }

    @Test
    public void testRoute1PM() {
        assertScheduleSize(Shuttle.ROUTE_1_PM, Station.DAY_1_NORTH, 7);
        assertScheduleSize(Shuttle.ROUTE_1_PM, Station.TRB, 7);
        assertScheduleSize(Shuttle.ROUTE_1_PM, Station.BLACKFOOT, 6);

        assertTrue(Shuttle.ROUTE_1_PM.isAvailable(Station.DAY_1_NORTH, Station.TRB));
        assertTrue(Shuttle.ROUTE_1_PM.isAvailable(Station.DAY_1_NORTH, Station.BLACKFOOT));
        assertTrue(Shuttle.ROUTE_1_PM.isAvailable(Station.TRB, Station.BLACKFOOT));
        assertTrue(Shuttle.ROUTE_1_PM.isAvailable(Station.TRB, Station.DAY_1_NORTH));
        assertTrue(Shuttle.ROUTE_1_PM.isAvailable(Station.BLACKFOOT, Station.DAY_1_NORTH));
        assertTrue(Shuttle.ROUTE_1_PM.isAvailable(Station.BLACKFOOT, Station.TRB));

        List<Schedule> fromDay1NorthToTrbAt230 = Shuttle.ROUTE_1_PM.getSchedules(Station.DAY_1_NORTH, Station.TRB, LocalTime.of(14, 30));
        assertEquals(fromDay1NorthToTrbAt230.size(), 6);
        assertSchedule(fromDay1NorthToTrbAt230.get(0), Station.DAY_1_NORTH, Station.TRB, 14, 45);
        assertSchedule(fromDay1NorthToTrbAt230.get(1), Station.DAY_1_NORTH, Station.TRB, 15, 25);
        assertSchedule(fromDay1NorthToTrbAt230.get(2), Station.DAY_1_NORTH, Station.TRB, 16, 15);
        assertSchedule(fromDay1NorthToTrbAt230.get(3), Station.DAY_1_NORTH, Station.TRB, 17, 15);
        assertSchedule(fromDay1NorthToTrbAt230.get(4), Station.DAY_1_NORTH, Station.TRB, 18, 5);
        assertSchedule(fromDay1NorthToTrbAt230.get(5), Station.DAY_1_NORTH, Station.TRB, 18, 55);

        List<Schedule> fromTrbToBlackfootAt435 = Shuttle.ROUTE_1_PM.getSchedules(Station.TRB, Station.BLACKFOOT, LocalTime.of(16, 35));
        assertEquals(fromTrbToBlackfootAt435.size(), 2);
        assertSchedule(fromTrbToBlackfootAt435.get(0), Station.TRB, Station.BLACKFOOT, 17, 35);
        assertSchedule(fromTrbToBlackfootAt435.get(1), Station.TRB, Station.BLACKFOOT, 18, 25);
    }

    private void assertScheduleSize(Shuttle shuttle, Station station, int count) {
        if (count == 0) {
            assertThat(shuttle.getSchedules().get(station), is(nullValue()));
        } else {
            assertThat(shuttle.getSchedules().get(station).size(), is(count));
        }
    }

}