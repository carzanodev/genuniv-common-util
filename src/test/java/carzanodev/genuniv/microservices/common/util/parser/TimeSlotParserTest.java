package carzanodev.genuniv.microservices.common.util.parser;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeSlotParserTest {

    @Test
    public void correctStringToTimeslotParsing() {
        Set<TimeSlotSchedule> set1 = TimeSlotParser.toTimeSlotSchedules("1|19-20,3|19-20,5|19-20");
        Set<TimeSlotSchedule> set2 = TimeSlotParser.toTimeSlotSchedules("2|8:30-10,4|8:30-10");

        assertEquals(set1.size(), 3);
        assertEquals(set2.size(), 2);

        TimeSlotSchedule[] set1Array = set1.toArray(TimeSlotSchedule[]::new);
        assertEquals(set1Array[0].getDayOfWeek(), DayOfWeek.MONDAY);
        assertEquals(set1Array[0].getStart(), LocalTime.of(19, 0));
        assertEquals(set1Array[0].getEnd(), LocalTime.of(20, 0));
        assertEquals(set1Array[1].getDayOfWeek(), DayOfWeek.WEDNESDAY);
        assertEquals(set1Array[1].getStart(), LocalTime.of(19, 0));
        assertEquals(set1Array[1].getEnd(), LocalTime.of(20, 0));
        assertEquals(set1Array[2].getDayOfWeek(), DayOfWeek.FRIDAY);
        assertEquals(set1Array[2].getStart(), LocalTime.of(19, 0));
        assertEquals(set1Array[2].getEnd(), LocalTime.of(20, 0));

        TimeSlotSchedule[] set2Array = set2.toArray(TimeSlotSchedule[]::new);
        assertEquals(set2Array[0].getDayOfWeek(), DayOfWeek.TUESDAY);
        assertEquals(set2Array[0].getStart(), LocalTime.of(8, 30));
        assertEquals(set2Array[0].getEnd(), LocalTime.of(10, 0));
        assertEquals(set2Array[1].getDayOfWeek(), DayOfWeek.THURSDAY);
        assertEquals(set2Array[1].getStart(), LocalTime.of(8, 30));
        assertEquals(set2Array[1].getEnd(), LocalTime.of(10, 0));
    }

    @Test
    public void correctTimeslotToStringParsing() {
        Set<TimeSlotSchedule> set = new LinkedHashSet<>();
        set.add(new TimeSlotSchedule(DayOfWeek.MONDAY, LocalTime.of(19, 0), LocalTime.of(20, 0)));
        set.add(new TimeSlotSchedule(DayOfWeek.WEDNESDAY, LocalTime.of(19, 0), LocalTime.of(20, 0)));
        set.add(new TimeSlotSchedule(DayOfWeek.FRIDAY, LocalTime.of(19, 0), LocalTime.of(20, 0)));

        assertEquals("1|19-20,3|19-20,5|19-20", TimeSlotParser.toTimeSlotString(set));

    }

}
