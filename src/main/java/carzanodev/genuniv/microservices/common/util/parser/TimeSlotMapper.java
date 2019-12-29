package carzanodev.genuniv.microservices.common.util.parser;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public class TimeSlotMapper {

    // With natural integer ordering
    private final NavigableMap<Integer, TimeSlotSchedule> scheduleMap = new TreeMap<>();

    public TimeSlotMapper(Collection<TimeSlotSchedule> defaultSchedules) throws ScheduleConflictException {
        putSchedules(defaultSchedules);
    }

    public void putSchedules(@NonNull Collection<TimeSlotSchedule> schedules) throws ScheduleConflictException {
        for (TimeSlotSchedule s : schedules) {
            putSchedule(s);
        }
    }

    public void putSchedule(@NonNull TimeSlotSchedule newSchedule) throws ScheduleConflictException {
        int key = newSchedule.getKey();

        Entry<Integer, TimeSlotSchedule> lowerEntry = scheduleMap.lowerEntry(key);
        if (lowerEntry != null) {
            TimeSlotSchedule schedBefore = lowerEntry.getValue();
            if (schedBefore.isConflict(newSchedule)) {
                throw new ScheduleConflictException(schedBefore, newSchedule);
            }
        }

        Entry<Integer, TimeSlotSchedule> higherEntry = scheduleMap.higherEntry(key);
        if (higherEntry != null) {
            TimeSlotSchedule schedAfter = higherEntry.getValue();
            if (schedAfter.isConflict(newSchedule)) {
                throw new ScheduleConflictException(schedAfter, newSchedule);
            }
        }

        scheduleMap.put(key, newSchedule);
    }

    public Collection<TimeSlotSchedule> getSchedules() {
        return scheduleMap.values();
    }

    public static class ScheduleConflictException extends Exception {
        public ScheduleConflictException(TimeSlotSchedule s1, TimeSlotSchedule s2) {
            super("Schedule conflict has occurred on the following schedules: " + s1.toString() + " & " + s2.toString());
        }
    }

}
