package lk.ijse.gdse.bo.exception;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(){

    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
