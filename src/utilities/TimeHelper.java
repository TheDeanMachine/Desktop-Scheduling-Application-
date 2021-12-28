package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;


public abstract class TimeHelper {

    public static ObservableList<Month> getMonths() {
        ObservableList<Month> months = FXCollections.observableArrayList();
        months.addAll(Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE, Month.JULY,
                Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
        return months;
    }

    public static ObservableList<LocalTime> initializeTimeSlots() {
        ObservableList<LocalTime> timeSlots = FXCollections.observableArrayList();
        LocalTime time = LocalTime.of(8, 0, 0);
        timeSlots.add(time); // starts at 8am
        for (int x = 15; x <= 840; x+=15) {
                LocalTime min = time.plusMinutes(x);
                timeSlots.add(min);
        }
        return timeSlots;
    }

    public static void check(){

        // EST ?????

        LocalTime startTime = LocalTime.parse("8:00:00"); // 8am
        LocalTime endTime = LocalTime.parse("22:00:00"); // 10pm

//        while (startTime.isBefore(endTime)) {
//            if (!timeSlots.get(startTime) || !timeSlots.get(endTime)) {
//                System.out.println("Appointment not available" + " start time: " + strTime + " end time: " + edTime);
//                return;
//            }
//        }

    }

}


