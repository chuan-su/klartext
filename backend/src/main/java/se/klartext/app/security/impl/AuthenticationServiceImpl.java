package se.klartext.app.security.impl;

import org.springframework.stereotype.Service;
import se.klartext.app.security.api.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    @Override
    public String authenticate(String username, String password) {
        return null;
    }

    @Override
    public boolean isValidToken(String token) {
        return false;
    }
}
