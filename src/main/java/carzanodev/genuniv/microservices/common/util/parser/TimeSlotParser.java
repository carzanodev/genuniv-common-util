package carzanodev.genuniv.microservices.common.util.parser;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import carzanodev.genuniv.microservices.common.util.string.CommonConstant;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeSlotParser {

    public static String HOUR_TO_MIN_DELIMETER = ":";
    public static String START_TO_END_DELIMETER = "-";
    public static String DAY_TO_TIME_DELIMETER = "\\|";
    public static String TIMESLOT_TO_TIMESLOT_DELIMETER = CommonConstant.DB_MULTIDATA_DELIMITER.getStr();

    public static Set<TimeSlotSchedule> toTimeSlotSchedules(String timeSlotStr) {
        Set<TimeSlotSchedule> timeSlotSchedules = new LinkedHashSet<>();

        String[] timeslots = timeSlotStr.split(TIMESLOT_TO_TIMESLOT_DELIMETER);
        for (String t : timeslots) {
            // Perform necessary splits
            String[] dayToTimeSplit = t.split(DAY_TO_TIME_DELIMETER);
            String[] startToEndSplit = dayToTimeSplit[1].split(START_TO_END_DELIMETER);
            String[] startHrToMinSplit = startToEndSplit[0].split(HOUR_TO_MIN_DELIMETER);
            String[] endHrToMinSplit = startToEndSplit[1].split(HOUR_TO_MIN_DELIMETER);

            // Assign splits accordingly
            int dayOfWeek = Integer.parseInt(dayToTimeSplit[0]);
            int startHr = Integer.parseInt(startHrToMinSplit[0]);
            int startMin = startHrToMinSplit.length > 1 ? Integer.parseInt(startHrToMinSplit[1]) : 0;
            int endHr = Integer.parseInt(endHrToMinSplit[0]);
            int endMin = endHrToMinSplit.length > 1 ? Integer.parseInt(endHrToMinSplit[1]) : 0;

            // Create schedule object
            TimeSlotSchedule timeSlotSchedule =
                    new TimeSlotSchedule(
                            DayOfWeek.of(dayOfWeek),
                            LocalTime.of(startHr, startMin),
                            LocalTime.of(endHr, endMin));

            // Add schedule to set
            timeSlotSchedules.add(timeSlotSchedule);
        }

        return timeSlotSchedules;
    }

    public static String toTimeSlotString(Set<TimeSlotSchedule> timeSlots) {
        StringBuilder timeSlotSb = new StringBuilder();

        for (TimeSlotSchedule t : timeSlots) {
            // Get integer equivalent of object data
            int dayOfWeek = t.getDayOfWeek().getValue();
            int startHr = t.getStart().getHour();
            int startMin = t.getStart().getMinute();
            int endHr = t.getEnd().getHour();
            int endMin = t.getEnd().getMinute();

            // Form correctly as String
            timeSlotSb
                    .append(dayOfWeek)
                    .append(DAY_TO_TIME_DELIMETER.replace("\\", ""))
                    .append(startHr)
                    .append(startMin > 0 ? HOUR_TO_MIN_DELIMETER : "")
                    .append(startMin > 0 ? startMin : "")
                    .append(START_TO_END_DELIMETER)
                    .append(endHr)
                    .append(endMin > 0 ? HOUR_TO_MIN_DELIMETER : "")
                    .append(endMin > 0 ? endMin : "")
                    .append(TIMESLOT_TO_TIMESLOT_DELIMETER);
        }

        String timeSlotStr = timeSlotSb.toString();

        // Remove excess comma
        if (timeSlotStr.length() > 0) {
            timeSlotStr = timeSlotStr.substring(0, timeSlotStr.length() - 1);
        }

        return timeSlotStr;
    }


}
