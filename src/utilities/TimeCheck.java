package utilities;

import java.time.LocalDateTime;

@FunctionalInterface
public interface TimeCheck {
    boolean checkForAppointmentsWithin15(LocalDateTime time);
}
