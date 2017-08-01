package se.klartext.app.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import se.klartext.app.business.api.UserService;
import se.klartext.app.data.api.UserRepository;
import se.klartext.app.model.AuthToken;
import se.klartext.app.model.User;
import se.klartext.app.security.api.AuthenticationService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    private final AuthenticationService authService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationService authService) {
        this.userRepo = userRepository;
        this.authService = authService;
    }

    @Override
    public User register(User user) {
        return userRepo.save(user);
    }

    @Override
    public Optional<AuthToken> auth(User user) {

        Optional<AuthToken> authToken = Optional.empty();

        try{
            authToken = authService.authenticate(user.getEmail(),user.getPassword());
        }catch (AuthenticationException e){
            System.out.println("User Credentials Auth Failure");
        }finally {
            return authToken;
        }

    }

    @Override
    public User profile(Long userId) {
        return userRepo.findOne(userId);
    }
}
