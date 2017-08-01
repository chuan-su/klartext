package se.klartext.app.exception;

public class AccountRegistrationException  extends RuntimeException {

    public AccountRegistrationException(String message){
        super(message);
    }
}
