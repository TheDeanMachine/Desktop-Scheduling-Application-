package utilities;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeSlot {

    private LocalTime localTime;

    public TimeSlot(LocalTime localTime) {
        this.localTime = localTime;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return formatter.format(localTime);
    }


}
