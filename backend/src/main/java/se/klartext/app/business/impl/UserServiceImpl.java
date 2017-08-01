package se.klartext.app.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.klartext.app.business.api.UserService;
import se.klartext.app.data.api.UserRepository;
import se.klartext.app.exception.AccountRegistrationException;
import se.klartext.app.model.AuthToken;
import se.klartext.app.model.User;
import se.klartext.app.security.api.AuthenticationService;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    private final AuthenticationService authService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationService authService) {
        this.userRepo = userRepository;
        this.authService = authService;
    }

    @Override
    public User register(User user) {
        userRepo.findByEmail(user.getEmail())
                .ifPresent(u -> { throw new AccountRegistrationException("Email already exists"); });

        User newUser = User.builder()
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .name(user.getName())
                .build();

        return userRepo.save(newUser);
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
