package se.klartext.app.data.jpa.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import se.klartext.app.config.PersistenceConfig;
import se.klartext.app.data.jpa.entity.User;
import se.klartext.app.data.jpa.repository.api.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@Import(PersistenceConfig.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByEmailTest() throws Exception {
        User newUser = User.builder()
                .email("test@mail.com")
                .name("Test User")
                .password("credentials")
                .build();
        this.entityManager.persist(newUser);
        Optional<User> user = userRepository.findByEmail("test@mail.com");
        assertThat(user.get().getName()).isEqualTo("Test User");
    }
}
