package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.*;
import java.time.format.DateTimeFormatter;


public abstract class TimeHelper {

    // Note: There are up to three time zones in effect.
    // Coordinated Universal Time (UTC) is used for storing the time in the database
    // The user’s local time is used for display purposes
    // Eastern Standard Time (EST) is used for the company’s office hours.

    public static ObservableList<Month> getMonths() {
        ObservableList<Month> months = FXCollections.observableArrayList();
        months.addAll(Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE, Month.JULY,
                Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
        return months;
    }

    public static ObservableList<LocalTime> initializeTimeSlots() {
        ObservableList<LocalTime> timeSlots = FXCollections.observableArrayList();

        LocalTime time = LocalTime.of(8, 0, 0);

        // You will need to turn that 08:00 time into a LocalDateTime (use “now”),
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), time);

        // Then convert to a ZonedDateTime on the Eastern zoneId
        ZoneId estZoneId = ZoneId.of("America/New_York");
        ZonedDateTime zonedDateTime = localDateTime.atZone(estZoneId);

        // Convert that to a ZonedDateTime on the system default ZoneId
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime localZonedTime = zonedDateTime.withZoneSameInstant(zone);

        // Then pull out the LocalTime from the localDateTime
        LocalTime localTime = localZonedTime.toLocalTime();

        timeSlots.add(localTime); // starts at 8am
        for (int x = 15; x <= 840; x+=15) {
                LocalTime min = localTime.plusMinutes(x);
                timeSlots.add(min);
        }
        return timeSlots;
    }


    // Local time will be checked against EST business hours before they are stored in the database as UTC.
    public static boolean checkBusinessHours(LocalDateTime start, LocalDateTime end){
        // Business hours
        LocalTime startTime = LocalTime.parse("8:00:00"); // 8am
        LocalTime endTime = LocalTime.parse("22:00:00"); // 10pm



        return false;
    }

    public static void main(String[] args) {

        LocalDateTime currentDateTime = LocalDateTime.now();

        ZoneId pstZoneId = ZoneId.of("America/Los_Angeles");
        ZoneId estZoneId = ZoneId.of("America/New_York");


        ZonedDateTime pstZoneTime = currentDateTime.atZone(pstZoneId);
        ZonedDateTime estZoneTime = pstZoneTime.withZoneSameInstant(estZoneId);       //EST Time


        DateTimeFormatter globalFormat = DateTimeFormatter.ofPattern("hh:mma z");
        DateTimeFormatter etFormat = DateTimeFormatter.ofPattern("hh:mma z");

        System.out.println(globalFormat.format(pstZoneTime));
        System.out.println(etFormat.format(estZoneTime));

    }

}


