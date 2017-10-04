package se.klartext.app.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import se.klartext.app.data.jpa.repository.api.AuthTokenRepository;
import se.klartext.app.data.jpa.entity.AuthToken;
import se.klartext.app.security.api.AuthenticationService;
import se.klartext.app.security.util.TokenGenerator;

import java.time.LocalDateTime;
import java.util.Optional;

public class AuthenticationServiceImpl implements AuthenticationService{

    private final AuthenticationManager authenticationManager;

    private final AuthTokenRepository authTokenRepo;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager,AuthTokenRepository authTokenRepo) {
        this.authenticationManager = authenticationManager;
        this.authTokenRepo = authTokenRepo;
    }

    @Override
    public Optional<AuthToken> authenticate(String username, String password) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(username,password);

        authentication = authenticationManager.authenticate(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Optional<String> token = TokenGenerator.getInstance().generateToken(userDetails.getPassword());

        if(token.isPresent()){
            AuthToken authToken = authTokenRepo.findByUserId(userDetails.getUser().getId())
                    .orElse(AuthToken.builder()
                        .user(userDetails.getUser())
                        .expiresAt(LocalDateTime.now().plusMonths(1))
                        .build()
                    );
            authToken.setToken(token.get());
            return Optional.of(authTokenRepo.save(authToken));
        }

        return Optional.empty();
    }

    @Override
    public Optional<UserDetails> authenticateWithToken(String token) {

        Authentication authentication = new PreAuthenticatedAuthenticationToken(token,token);

        authentication = authenticationManager.authenticate(authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return Optional.of((UserDetails)authentication.getPrincipal());
    }
}
