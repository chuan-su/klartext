package se.klartext.app.security.api;

public interface AuthenticationService {

    String authenticate(String username,String password);

    boolean isValidToken(String token);
}
