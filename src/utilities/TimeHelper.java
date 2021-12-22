package utilities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.time.Month;

public abstract class TimeHelper {

    public static ObservableList<Month> getMonths() {
        ObservableList<Month> months = FXCollections.observableArrayList();
        months.addAll(Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE, Month.JULY,
                Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
        return months;
    }

    LocalTime startTime = LocalTime.parse("8:00:00"); //8am
    LocalTime endTime = LocalTime.parse("17:00:00"); //5pm

    public static ObservableList initializeTimeSlots() {
        ObservableList<LocalTime> timeSlots = FXCollections.observableArrayList();

        LocalTime time = LocalTime.of(8, 0, 0);
        for (int i = 0; i < 9; i++) {
            LocalTime temp = time.plusHours(i);
            timeSlots.add(temp);

            for (int x = 15; x <= 45; x+=15) {
                LocalTime temp2 = temp.plusMinutes(x);
                timeSlots.add(temp2);
            }
        }
        return timeSlots;
    }

}


