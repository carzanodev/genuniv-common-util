package carzanodev.genuniv.microservices.common.util.parser;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import carzanodev.genuniv.microservices.common.util.parser.TimeSlotMapper.ScheduleConflictException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeSlotMapperTest {

    @Test
    public void correctScheduleAllocation() throws ScheduleConflictException {
        TimeSlotMapper mapper = new TimeSlotMapper();
        mapper.putSchedule(new TimeSlotSchedule(DayOfWeek.MONDAY, LocalTime.of(1, 30), LocalTime.of(2, 30)));
        mapper.putSchedule(new TimeSlotSchedule(DayOfWeek.MONDAY, LocalTime.of(2, 30), LocalTime.of(3, 30)));
        mapper.putSchedule(new TimeSlotSchedule(DayOfWeek.MONDAY, LocalTime.of(4, 0), LocalTime.of(5, 0)));
        mapper.putSchedule(new TimeSlotSchedule(DayOfWeek.TUESDAY, LocalTime.of(1, 45), LocalTime.of(3, 0)));
        mapper.putSchedule(new TimeSlotSchedule(DayOfWeek.TUESDAY, LocalTime.of(3, 0), LocalTime.of(4, 0)));

        assertEquals(5, mapper.getSchedules().size());
    }

    @Test
    public void errorOnBadScheduleAllocation_throwException() throws ScheduleConflictException {
        TimeSlotMapper mapper = new TimeSlotMapper();
        mapper.putSchedule(new TimeSlotSchedule(DayOfWeek.MONDAY, LocalTime.of(1, 30), LocalTime.of(2, 30)));
        mapper.putSchedule(new TimeSlotSchedule(DayOfWeek.MONDAY, LocalTime.of(2, 30), LocalTime.of(3, 30)));
        mapper.putSchedule(new TimeSlotSchedule(DayOfWeek.MONDAY, LocalTime.of(4, 0), LocalTime.of(5, 0)));
        mapper.putSchedule(new TimeSlotSchedule(DayOfWeek.TUESDAY, LocalTime.of(1, 45), LocalTime.of(3, 0)));
        mapper.putSchedule(new TimeSlotSchedule(DayOfWeek.TUESDAY, LocalTime.of(3, 0), LocalTime.of(4, 0)));

        assertThrows(ScheduleConflictException.class, () -> mapper.putSchedule(new TimeSlotSchedule(DayOfWeek.MONDAY, LocalTime.of(2, 0), LocalTime.of(5, 0))));
        assertThrows(ScheduleConflictException.class, () -> mapper.putSchedule(new TimeSlotSchedule(DayOfWeek.MONDAY, LocalTime.of(4, 0), LocalTime.of(4, 30))));
        assertThrows(ScheduleConflictException.class, () -> mapper.putSchedule(new TimeSlotSchedule(DayOfWeek.TUESDAY, LocalTime.of(2, 0), LocalTime.of(3, 30))));
        assertThrows(ScheduleConflictException.class, () -> mapper.putSchedule(new TimeSlotSchedule(DayOfWeek.TUESDAY, LocalTime.of(3, 0), LocalTime.of(3, 30))));
    }

}
