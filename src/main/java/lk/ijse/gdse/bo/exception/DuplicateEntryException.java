package lk.ijse.gdse.bo.exception;

public class DuplicateEntryException extends RuntimeException{
    public DuplicateEntryException() {
    }

    public DuplicateEntryException(String message) {
        super(message);
    }
}
