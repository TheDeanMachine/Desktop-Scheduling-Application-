package utilities;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is used to display formatted time slots.
 */
public class TimeSlot {

    // time reference field
    private LocalTime localTime;

    // constructor
    public TimeSlot(LocalTime localTime) {
        this.localTime = localTime;
    }

    /**
     * Used to retrieve the local time reference.
     * @return the set localtime.
     */
    public LocalTime getLocalTime() {
        return localTime;
    }

    /**
     * Used to return LocalTime as a formatted string.
     * When the list of time objects is displayed in the combo boxes,
     * this to string method displays them in more human-readable way.
     * @return formatted local time string.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return formatter.format(localTime);
    }


}
