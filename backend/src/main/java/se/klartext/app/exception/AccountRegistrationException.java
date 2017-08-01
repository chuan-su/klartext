package se.klartext.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AccountRegistrationException  extends RuntimeException {
    public AccountRegistrationException(String message){
        super(message);
    }
}
