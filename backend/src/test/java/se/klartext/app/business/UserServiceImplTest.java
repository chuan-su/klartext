package se.klartext.app.business;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import se.klartext.app.business.api.UserService;
import se.klartext.app.business.impl.UserServiceImpl;
import se.klartext.app.data.api.UserRepository;
import se.klartext.app.exception.AccountRegistrationException;
import se.klartext.app.model.User;
import se.klartext.app.security.api.AuthenticationService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    private UserService userService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private AuthenticationService authService;

    @Before
    public void setUp(){
        userService = new UserServiceImpl(userRepo,authService);
    }

    @Test(expected = AccountRegistrationException.class)
    public void throwUserRegistrationException(){
        User user = User.builder()
                .name("chuan")
                .email("chuan@gmail.com")
                .password("testcredentials")
                .build();
        Mockito.when(userRepo.findByEmail(user.getEmail()))
                .thenReturn(java.util.Optional.ofNullable(user));

        userService.register(user);
    }
}
