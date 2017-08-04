package se.klartext.app.security;

import org.apache.http.auth.AUTH;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.omg.CORBA.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import se.klartext.app.data.api.AuthTokenRepository;
import se.klartext.app.data.api.UserRepository;
import se.klartext.app.model.AuthToken;
import se.klartext.app.model.User;
import se.klartext.app.security.api.AuthenticationService;
import se.klartext.app.security.impl.AuthenticationServiceImpl;
import se.klartext.app.security.impl.UserDetailsImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
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

        user.setId(Long.valueOf(1));

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
