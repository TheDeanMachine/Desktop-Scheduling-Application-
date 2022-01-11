package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.*;
import java.time.format.DateTimeFormatter;

// Note: There are up to three time zones in effect.
// Coordinated Universal Time (UTC) is used for storing the time in the database
// The user’s local time is used for display purposes
// Eastern Standard Time (EST) is used for the company’s office hours.

/**
 * This class is used as a helper class for time related operations.
 */
public abstract class TimeHelper {
    private static ObservableList<TimeSlot> timeSlots = null;

    /**
     * This method is used to generate a list of month enums.
     * @return list of month enums.
     */
    public static ObservableList<Month> getMonths() {
        ObservableList<Month> months = FXCollections.observableArrayList();
        months.addAll(Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE, Month.JULY,
                Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
        return months;
    }

    /**
     * This method generates a list of local time from 8am to 10pm as EST.
     * @return list of local time based on EST business hours.
     */
    public static ObservableList<TimeSlot> initializeTimeSlots() {
        if(timeSlots == null || timeSlots.size() == 0) { // initialize the list once to improve performance
            timeSlots = FXCollections.observableArrayList();

            LocalTime time = LocalTime.of(8, 0, 0);
            LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), time);

            ZoneId estZoneId = ZoneId.of("America/New_York");
            ZonedDateTime zonedDateTime = localDateTime.atZone(estZoneId);

            ZoneId zone = ZoneId.systemDefault();
            ZonedDateTime localZonedTime = zonedDateTime.withZoneSameInstant(zone);

            LocalTime localTime = localZonedTime.toLocalTime();

            timeSlots.add(new TimeSlot(localTime)); // starts at 8am
            for (int x = 15; x < 841; x += 15) {
                LocalTime min = localTime.plusMinutes(x); // adds increments of 15 min till 10pm
                timeSlots.add(new TimeSlot(min));
            }
        }
        return timeSlots;
    }

    /**
     * This method is used to format date time.
     * @param time the date time to be formatted.
     * @return formatted date time.
     */
    public static String getFormattedDateTime(LocalDateTime time){
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = time.atZone(localZoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma z");

        return formatter.format(zonedDateTime);
    }

    /**
     * This method is used to format date time.
     * @param time the date time to be formatted.
     * @param zone The destination zone.
     * @return formatted date time.
     */
    public static String getFormattedDateTime(LocalDateTime time, ZoneId zone){
        ZonedDateTime zonedDateTime = time.atZone(ZoneId.systemDefault());
        ZonedDateTime dateTime = zonedDateTime.withZoneSameInstant(zone);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma z");

        return formatter.format(dateTime);
    }

    /**
     * This method is used to check for overlapping appointments.
     * The start and end time of the appointment being scheduled is checked against
     * the appointments in the database. The four parameters are passed into this method for validation.
     * @param currentStart the start of the appointment in the database.
     * @param newStart the start of the appointment being scheduled.
     * @param currentEnd the end of the appointment in the database.
     * @param newEnd the end of the appointment being scheduled.
     * @return True if there is an overlap, False if there is not.
     */
    public static boolean checkAppointmentTime(LocalDateTime currentStart, LocalDateTime newStart,
                                               LocalDateTime currentEnd, LocalDateTime newEnd ){

        if (((newStart.isAfter(currentStart) || newStart.isEqual(currentStart)) && newStart.isBefore(currentEnd))){
            return true;
        } else if(newEnd.isAfter(currentStart) && (newEnd.isBefore(currentEnd) || newEnd.isEqual(currentEnd))) {
            return true;
        } else if (newStart.isBefore(currentStart) && newEnd.isAfter(currentEnd)) {
            return true;
        } else if (newStart.isEqual(currentStart) && newEnd.isEqual(currentEnd)) {
            return true;
        } else if (newStart.isAfter(currentStart) && newEnd.isBefore(currentEnd)) {
            return true;
        } else {
            return false;
        }
    }

//    // variable and method for checking time difference, prior to lambda
//    public static long timeDifference;
//    public static boolean checkForAppointmentsWithin15(LocalDateTime timeToCheck){
//
//        timeDifference = ChronoUnit.MINUTES.between(LocalDateTime.now(), timeToCheck);
//
//        if(timeDifference > 0 && timeDifference < 16 ) {
//            return true;
//        }
//        return false;
//    }


}


