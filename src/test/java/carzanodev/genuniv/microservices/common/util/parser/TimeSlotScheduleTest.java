package carzanodev.genuniv.microservices.common.util.parser;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeSlotScheduleTest {

    @Test
    public void correctGeneratedKey() {
        TimeSlotSchedule schedule1 = new TimeSlotSchedule(DayOfWeek.MONDAY, LocalTime.of(1, 30), LocalTime.of(2, 30));
        assertEquals(101300230, schedule1.getKey());

        TimeSlotSchedule schedule2 = new TimeSlotSchedule(DayOfWeek.TUESDAY, LocalTime.of(15, 1), LocalTime.of(17, 01));
        assertEquals(215011701, schedule2.getKey());
    }

}
