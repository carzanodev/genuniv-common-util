package carzanodev.genuniv.microservices.common.util.parser;

import java.time.DayOfWeek;
import java.time.LocalTime;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TimeSlotSchedule {

    private final DayOfWeek dayOfWeek;
    private final LocalTime start;
    private final LocalTime end;

    private final int key;

    public TimeSlotSchedule(DayOfWeek dayOfWeek, LocalTime start, LocalTime end) {
        this.dayOfWeek = dayOfWeek;
        this.start = start;
        this.end = end;
        this.key = generateKey(this.dayOfWeek, this.start, this.end);
    }

    private static int generateKey(DayOfWeek dayOfWeek, LocalTime start, LocalTime end) {
        return
                Integer.parseInt(
                        String.format("%s%2s%2s%2s%2s",
                                String.valueOf(dayOfWeek.getValue()),
                                String.valueOf(start.getHour()), String.valueOf(start.getMinute()),
                                String.valueOf(end.getHour()), String.valueOf(end.getMinute()))
                                .replaceAll(" ", "0"));
    }

    public boolean isConflict(TimeSlotSchedule schedule) {
        return isDayConflict(schedule.getDayOfWeek()) &&
                (isTimeConflict(schedule.getStart()) || isTimeConflict(schedule.getEnd()));
    }

    public boolean isDayConflict(DayOfWeek day) {
        return day.equals(this.dayOfWeek);
    }

    public boolean isTimeConflict(LocalTime time) {
        return time.isAfter(this.start) && time.isBefore(this.end);
    }

}
