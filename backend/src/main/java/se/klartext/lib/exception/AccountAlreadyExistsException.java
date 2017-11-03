package se.klartext.lib.exception;

public class AccountAlreadyExistsException extends AccountRegistrationException {

    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
