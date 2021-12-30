package utilities;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeSlot {

    private LocalTime localTime;

    public TimeSlot(LocalTime localTime) {
        this.localTime = localTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");
        return formatter.format(localTime);
    }


}
