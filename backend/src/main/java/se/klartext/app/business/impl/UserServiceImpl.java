package se.klartext.app.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.klartext.app.business.api.UserService;
import se.klartext.app.data.api.UserRepository;
import se.klartext.app.lib.exception.AccountAlreadyExistsException;
import se.klartext.app.model.AuthToken;
import se.klartext.app.model.User;
import se.klartext.app.security.api.AuthenticationService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    private final AuthenticationService authService;


    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AuthenticationService authService) {
        this.userRepo = userRepository;
        this.authService = authService;
    }

    @Override
    public User register(User user) {
        userRepo.findByEmail(user.getEmail())
                .ifPresent(u -> { throw new AccountAlreadyExistsException("Email already exists"); });

        User newUser = User.builder()
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .name(user.getName())
                .build();

        return userRepo.save(newUser);
    }

    @Override
    public Optional<AuthToken> auth(User user) throws AuthenticationException {
        return authService.authenticate(user.getEmail(),user.getPassword());
    }

    @Override
    public Optional<User> profile(Long userId) {
        return userRepo.findOne(userId);
    }
}
