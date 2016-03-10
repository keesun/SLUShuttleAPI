package me.whiteship.domain;

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

    Station trb = Station.builder().name("TRB").build();
    Station blackfoot = Station.builder().name("Blackfoot").build();
    Station arizona = Station.builder().name("Arizona").build();
    Station day1North = Station.builder().name("Day 1 North").build();

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
        Shuttle shuttle = Shuttle.builder()
                .number(1)
                .description("TRB - Blackfoot - Day 1 North \n" +
                        "AM Blackfoot & Arizona call-outs")
                .stations(new Station[]{trb, blackfoot, arizona, day1North})
                .callouts(new Boolean[]{false, true, true, false})
                .build();
        shuttle.addSchedules(trb, "6:55 AM", "7:35 AM", "8:15 AM", "8:55 AM", "9:35 AM", "10:15 AM", "10:50 AM",
                "11:25 AM", "12:00 PM", "12:45 PM", "1:20 PM", "1:50 PM");
        shuttle.addSchedules(day1North, "7:15 AM", "8:00 AM", "8:40 AM", "9:20 AM", "9:55 AM", "10:30 AM",
                "11:05 AM", "11:40 AM", "12:15 PM", "1:00 PM", "1:35 PM");

        assertThat(shuttle.getNumber(), is(1));
        assertThat(shuttle.getSchedules().get(trb).size(), is(12));
        assertThat(shuttle.getSchedules().get(day1North).size(), is(11));
        assertThat(shuttle.getSchedules().get(blackfoot), is(nullValue()));
        assertThat(shuttle.getSchedules().get(arizona), is(nullValue()));

        assertTrue(shuttle.available(trb, blackfoot));
        assertTrue(shuttle.available(trb, arizona));
        assertTrue(shuttle.available(trb, day1North));
        // call-out station means drop only
        assertFalse(shuttle.available(blackfoot, arizona));
        assertFalse(shuttle.available(blackfoot, day1North));
        assertFalse(shuttle.available(blackfoot, trb));
        assertFalse(shuttle.available(arizona, day1North));
        assertFalse(shuttle.available(arizona, trb));
        assertFalse(shuttle.available(arizona, blackfoot));
        // support circular schedule.
        assertTrue(shuttle.available(day1North, trb));
        assertTrue(shuttle.available(day1North, blackfoot));
        assertTrue(shuttle.available(day1North, arizona));

        // TODO get schedule
    }

    @Test
    public void testRoute1PM() {
        Shuttle shuttle = Shuttle.builder()
                .number(1)
                .description("PM: Day 1 North - TRB - Balckfoot")
                .stations(new Station[]{day1North, trb, blackfoot})
                .callouts(new Boolean[]{false, false, false})
                .build();
        shuttle.addSchedules(day1North, "2:10 PM", "2:45 PM", "3:25 PM", "4:15 PM", "5:15 PM", "6:05 PM", "6:55 PM");
        shuttle.addSchedules(trb, "2:25 PM", "3:05 PM", "3:45 PM", "4:35 PM", "5:35 PM", "6:25 PM");
        shuttle.addSchedules(blackfoot, "2:40 PM", "3:20 PM", "4:05 PM", "5:05 PM", "5:55 PM", "6:45 PM");

        assertThat(shuttle.getSchedules().get(day1North).size(), is(7));
        assertThat(shuttle.getSchedules().get(trb).size(), is(6));
        assertThat(shuttle.getSchedules().get(blackfoot).size(), is(6));

        assertTrue(shuttle.available(day1North, trb));
        assertTrue(shuttle.available(day1North, blackfoot));
        assertTrue(shuttle.available(trb, blackfoot));
        assertTrue(shuttle.available(trb, day1North));
        assertTrue(shuttle.available(blackfoot, day1North));
        assertTrue(shuttle.available(blackfoot, trb));

        // TODO get schedule
    }

}