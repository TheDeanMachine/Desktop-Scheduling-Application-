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

    private static ObservableList<LocalTime> timeSlots = null;

    public static ObservableList<LocalTime> initializeTimeSlots() {
        if(timeSlots == null || timeSlots.size() == 0) {
            timeSlots = FXCollections.observableArrayList();

            LocalTime time = LocalTime.of(8, 0, 0);
            LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), time);

            ZoneId estZoneId = ZoneId.of("America/New_York");
            ZonedDateTime zonedDateTime = localDateTime.atZone(estZoneId);

            ZoneId zone = ZoneId.systemDefault();
            ZonedDateTime localZonedTime = zonedDateTime.withZoneSameInstant(zone);

            LocalTime localTime = localZonedTime.toLocalTime();

            timeSlots.add(localTime); // starts at 8am
            for (int x = 15; x < 841; x += 15) {
                LocalTime min = localTime.plusMinutes(x); // adds increments of 15 min till 10pm
                timeSlots.add(min);
            }
        }
        return timeSlots;
    }


    public static ObservableList<String> getFormattedTime(){
        if(timeSlots == null || timeSlots.size() == 0) {
            timeSlots = FXCollections.observableArrayList();

            LocalTime time = LocalTime.of(8, 0, 0);
            LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), time);

            ZoneId estZoneId = ZoneId.of("America/New_York");
            ZonedDateTime zonedDateTime = localDateTime.atZone(estZoneId);

            ZoneId zone = ZoneId.systemDefault();
            ZonedDateTime localZonedTime = zonedDateTime.withZoneSameInstant(zone);

            LocalTime localTime = localZonedTime.toLocalTime();

            timeSlots.add(localTime); // starts at 8am
            for (int x = 15; x < 841; x += 15) {
                LocalTime min = localTime.plusMinutes(x); // adds increments of 15 min till 10pm
                timeSlots.add(min);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma z");
            formatter.format(zonedDateTime);

        }
        return null;
    }

    // Local time will be checked against EST business hours before they are stored in the database as UTC.
    public static boolean checkBusinessHours(LocalDateTime start, LocalDateTime end){
        // Business hours
        LocalTime startTime = LocalTime.parse("8:00:00"); // 8am
        LocalTime endTime = LocalTime.parse("22:00:00"); // 10pm



        return false;
    }


    public static String getFormattedDateTime(LocalDateTime time){
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = time.atZone(localZoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma z");

        return formatter.format(zonedDateTime);
    }


    ///////// test area ////////////
    public static void main(String[] args) {

        LocalDateTime currentDateTime = LocalDateTime.now();

        ZoneId pstZoneId = ZoneId.of("America/Los_Angeles");
        ZoneId estZoneId = ZoneId.of("America/New_York");


        ZonedDateTime pstZoneTime = currentDateTime.atZone(pstZoneId);
        ZonedDateTime estZoneTime = pstZoneTime.withZoneSameInstant(estZoneId);       //EST Time


        DateTimeFormatter globalFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mma z");
        DateTimeFormatter etFormat = DateTimeFormatter.ofPattern("hh:mma z");

        System.out.println(globalFormat.format(pstZoneTime));
        System.out.println(etFormat.format(estZoneTime));

    }

}


