package utilities;

import java.time.LocalDateTime;

/**
 * Functional interface used for the lambda expression.
 */
@FunctionalInterface
public interface TimeCheck {
    boolean checkForAppointmentsWithin15(LocalDateTime time);
}
