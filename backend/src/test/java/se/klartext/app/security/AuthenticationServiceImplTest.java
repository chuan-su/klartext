package se.klartext.app.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import se.klartext.app.data.jpa.repository.api.AuthTokenRepository;
import se.klartext.app.data.jpa.repository.api.UserRepository;
import se.klartext.app.data.jpa.entity.AuthToken;
import se.klartext.app.data.jpa.entity.User;
import se.klartext.app.security.api.AuthenticationService;
import se.klartext.app.security.impl.AuthenticationServiceImpl;
import se.klartext.app.security.impl.UserDetailsImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.notNull;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceImplTest {

    private AuthenticationService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AuthTokenRepository authTokenRepo;

    @Mock
    private UserRepository userRepository;

    @Before
    public void setUp(){
        User user = User.builder()
                .email("chuan@outlook.com")
                .name("chuan")
                .password("klartext")
                .build();

        //user.setId(Long.valueOf(1));

        Mockito.when(authTokenRepo.findByUserId(Long.valueOf(1)))
                .thenReturn(
                        Optional.empty()
                );

        Mockito.when(authTokenRepo.save((AuthToken)notNull())).then(AdditionalAnswers.returnsFirstArg());

        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(authentication.getPrincipal())
                .thenReturn(new UserDetailsImpl(user));

        Mockito.when(authenticationManager.authenticate((UsernamePasswordAuthenticationToken)notNull()))
                .thenReturn(authentication);

        authService = new AuthenticationServiceImpl(authenticationManager,authTokenRepo);
    }

    @Test
    public void authenticateTest() throws Exception {
        String email = "chuan@outlook.com";

        Optional<AuthToken> authToken = authService.authenticate(email,"klartext");

        assertTrue(authToken.isPresent());
        assertThat(authToken.get().getUser().getEmail()).isEqualTo(email);
        assertThat(authToken.get().getToken()).isNotEmpty();
        assertThat(authToken.get().getExpiresAt()).isGreaterThan(LocalDateTime.now());
    }
}
