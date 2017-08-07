package se.klartext.app.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import se.klartext.app.config.PersistenceConifg;
import se.klartext.app.data.api.LikeRepository;
import se.klartext.app.model.Like;
import se.klartext.app.model.Post;
import se.klartext.app.model.User;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import(PersistenceConifg.class)
@DataJpaTest
public class LikeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LikeRepository likeRepo;

    @Test
    public void testSave() throws Exception {
        User user = User.builder().name("chuan").password("credentials").email("chuan@mail.se").build();
        user = entityManager.persist(user);

        Post post = Post.builder().body("test").interp("test interp").createdBy(user).build();
        post = entityManager.persist(post);

        Like like = Like.builder().user(user).post(post).build();
        Like likeEntity = likeRepo.save(like);

        assertThat(likeEntity.getId(),is(notNullValue()));
        assertThat(likeEntity.getCreatedAt(),is(notNullValue()));
        assertThat(likeEntity.getUpdatedAt(),is(notNullValue()));
        assertThat(likeEntity.getPost().getId(),is(post.getId()));
        assertThat(likeEntity.getUser().getId(),is(user.getId()));
    }
    @Test
    public void testFindByUserIdAndPostId() throws Exception {
        User user = User.builder().name("chuan").password("credentials").email("chuan@mail.se").build();
        user = entityManager.persist(user);

        Post post = Post.builder().body("test").interp("test interp").createdBy(user).build();
        post = entityManager.persist(post);

        Like like = Like.builder().user(user).post(post).build();
        like = entityManager.persist(like);

        Optional<Like> likeEntity = likeRepo.findByUserIdAndPostId(user.getId(),post.getId());
        assertTrue(likeEntity.isPresent());
        assertThat(likeEntity.get().getPost().getBody(),is(post.getBody()));
        assertThat(likeEntity.get().getUser().getEmail(),is(user.getEmail()));
    }
}
