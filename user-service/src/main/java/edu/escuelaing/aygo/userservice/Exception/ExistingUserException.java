package edu.escuelaing.aygo.userservice.Exception;

public class ExistingUserException extends RuntimeException {
    public ExistingUserException(String msg) {
        super(msg);
    }
}
