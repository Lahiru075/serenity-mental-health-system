package lk.ijse.gdse.bo.exception;

public class MissingFieldsException extends RuntimeException{
    public MissingFieldsException() {
    }

    public MissingFieldsException(String message) {
        super(message);
    }
}
