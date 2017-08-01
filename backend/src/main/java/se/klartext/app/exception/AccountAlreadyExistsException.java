package se.klartext.app.exception;

public class AccountAlreadyExistsException extends AccountRegistrationException {

    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
