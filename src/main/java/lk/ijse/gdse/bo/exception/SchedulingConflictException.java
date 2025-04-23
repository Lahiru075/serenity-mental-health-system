package lk.ijse.gdse.bo.exception;


public class SchedulingConflictException extends RuntimeException {
    public SchedulingConflictException() {
    }

    public SchedulingConflictException(String message) {
        super(message);
    }
}
