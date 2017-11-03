package se.klartext.business;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import se.klartext.business.api.UserService;
import se.klartext.business.impl.UserServiceImpl;
import se.klartext.domain.model.user.UserRepository;
import se.klartext.lib.exception.AccountRegistrationException;
import se.klartext.domain.model.user.User;
import se.klartext.security.api.AuthenticationService;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.notNull;

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
    public void accountRegistrationException(){
        User user = User.builder()
                .name("chuan")
                .email("chuan@gmail.com")
                .password("testcredentials")
                .build();

        Mockito.when(userRepo.findByEmail(user.getEmail()))
                .thenReturn(java.util.Optional.ofNullable(user));

        userService.register(user);
    }

    @Test
    public void registerTest() throws Exception{
        User user = User.builder()
                .name("chuan")
                .email("chuan@gmail.com")
                .password("testcredentials")
                .build();

        //user.setId(Long.valueOf(1));

        Mockito.when(userRepo.findByEmail(user.getEmail()))
                .thenReturn(Optional.empty());

        Mockito.when(userRepo.save((User)notNull())).thenReturn(user);

        user = userService.register(user);

        assertEquals(Long.valueOf(1),user.getId());
    }
}
