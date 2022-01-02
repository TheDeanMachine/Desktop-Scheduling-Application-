package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.*;
import java.time.format.DateTimeFormatter;

// Note: There are up to three time zones in effect.
// Coordinated Universal Time (UTC) is used for storing the time in the database
// The user’s local time is used for display purposes
// Eastern Standard Time (EST) is used for the company’s office hours.


public abstract class TimeHelper {
    private static ObservableList<TimeSlot> timeSlots = null;

    public static ObservableList<Month> getMonths() {
        ObservableList<Month> months = FXCollections.observableArrayList();
        months.addAll(Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE, Month.JULY,
                Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
        return months;
    }

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

    public static String getFormattedDateTime(LocalDateTime time){
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = time.atZone(localZoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma z");

        return formatter.format(zonedDateTime);
    }

    public static boolean checkAppointmentTime(LocalDateTime currentStart, LocalDateTime newStart,
                                               LocalDateTime currentEnd, LocalDateTime newEnd ){

        if ((newStart.isAfter(currentStart) && newEnd.isBefore(currentEnd)) ||
                (newStart.isAfter(currentStart) && newEnd.isAfter(currentEnd)) ||
                (newStart.isBefore(currentStart) && newEnd.isBefore(currentEnd)) ||
                (newStart.isBefore(currentStart) && newEnd.isAfter(currentEnd)) ||
                (newStart.isEqual(currentStart) && newEnd.isEqual(currentEnd)) ||
                (newStart.isEqual(currentStart) && newEnd.isBefore(currentEnd)) ||
                (newStart.isEqual(currentStart) && newEnd.isAfter(currentEnd)) ||
                (newStart.isAfter(currentStart) && newEnd.isEqual(currentEnd)) ||
                (newStart.isBefore(currentStart) && newEnd.isEqual(currentEnd))) {
            return false;
        } else {
            return true;
        }
    }



    //// TEST AREA //////////////////////////
    public static void main(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(localZoneId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma z");
        System.out.println(formatter.format(zonedDateTime));

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("hh:mma");
        System.out.println(formatter2.format(localDateTime));

        LocalTime localTime =   LocalTime.now();
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("hh:mma");
        System.out.println(formatter3.format(localTime));


    }

}


