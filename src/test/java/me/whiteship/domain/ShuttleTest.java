package me.whiteship.domain;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Keesun Baik
 */
public class ShuttleTest {

    private Station trb;
    private Station blackfoot;
    private Station arizona;
    private Station day1North;

    private Shuttle route1AM;
    private Shuttle route1PM;

    @Before
    public void setUp() {
        trb = Station.builder().name("TRB").build();
        blackfoot = Station.builder().name("Blackfoot").build();
        arizona = Station.builder().name("Arizona").build();
        day1North = Station.builder().name("Day 1 North").build();

        route1AM = Shuttle.builder()
                .number(1)
                .description("TRB - Blackfoot - Day 1 North \n" +
                        "AM Blackfoot & Arizona call-outs")
                .stations(new Station[]{trb, blackfoot, arizona, day1North})
                .callouts(new Boolean[]{false, true, true, false})
                .build();
        route1AM.addSchedules(trb, "6:55 AM", "7:35 AM", "8:15 AM", "8:55 AM", "9:35 AM", "10:15 AM", "10:50 AM",
                "11:25 AM", "12:00 PM", "12:45 PM", "1:20 PM", "1:50 PM");
        route1AM.addSchedules(day1North, "7:15 AM", "8:00 AM", "8:40 AM", "9:20 AM", "9:55 AM", "10:30 AM",
                "11:05 AM", "11:40 AM", "12:15 PM", "1:00 PM", "1:35 PM");

        route1PM = Shuttle.builder()
                .number(1)
                .description("PM: Day 1 North - TRB - Balckfoot")
                .stations(new Station[]{day1North, trb, blackfoot})
                .callouts(new Boolean[]{false, false, false})
                .build();
        route1PM.addSchedules(day1North, "2:10 PM", "2:45 PM", "3:25 PM", "4:15 PM", "5:15 PM", "6:05 PM", "6:55 PM");
        route1PM.addSchedules(trb, "2:25 PM", "3:05 PM", "3:45 PM", "4:35 PM", "5:35 PM", "6:25 PM");
        route1PM.addSchedules(blackfoot, "2:40 PM", "3:20 PM", "4:05 PM", "5:05 PM", "5:55 PM", "6:45 PM");
    }

    @Test
    public void testParsingTime() {
        String time = "6:55 AM";
        LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("h:mm a")
                .withLocale(Locale.ENGLISH)); // without Locale an exception may occur.
        assertThat(localTime.getHour(), is(6));
        assertThat(localTime.getMinute(), is(55));
    }

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

        // TODO get schedule
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

        // TODO get schedule
    }

    private void assertScheduleSize(Shuttle shuttle, Station station, int count) {
        if (count == 0) {
            assertThat(shuttle.getSchedules().get(station), is(nullValue()));
        } else {
            assertThat(shuttle.getSchedules().get(station).size(), is(count));
        }

    }

}