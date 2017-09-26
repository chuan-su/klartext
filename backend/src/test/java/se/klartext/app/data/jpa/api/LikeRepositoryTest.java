package se.klartext.app.data.jpa.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import se.klartext.app.config.PersistenceConfig;
import se.klartext.app.data.api.jpa.LikeRepository;
import se.klartext.app.model.Like;
import se.klartext.app.model.Post;
import se.klartext.app.model.User;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import(PersistenceConfig.class)
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
    @Test
    public void testFindByPostId() throws Exception {

        final User user = entityManager.persist(
                User.builder().name("chuan").password("credentials").email("chuan@mail.se").build()
        );

        final Post post = entityManager.persist(
                Post.builder().body("test1").interp("test interp1").createdBy(user).build()
        );

        User user1 = User.builder().name("testuser1").password("credentials1").email("testuser1@mail.se").build();
        User user2 = User.builder().name("testuser2").password("credentials2").email("testuser2@mail.se").build();
        User user3 = User.builder().name("testuser3").password("credentials3").email("testuser3@mail.se").build();

        Arrays.asList(user1,user2,user3).stream()
                .map(u -> entityManager.persist(u))
                .map(u -> Like.builder().post(post).user(u).build())
                .forEach(like -> entityManager.persist(like));

        Pageable pageable = new PageRequest(0,10);

        Page<Like> likes = likeRepo.findByPostId(post.getId(),pageable);
        assertThat(likes,hasProperty("content"));
        assertThat(likes.getTotalElements(),equalTo(Long.valueOf(3)));
    }

    @Test
    public void testCountByPostId() throws Exception {

        final User user = entityManager.persist(
                User.builder().name("user").password("credentials").email("user@mail.se").build()
        );

        final Post post = entityManager.persist(
                Post.builder().body("test1").interp("test interp1").createdBy(user).build()
        );

        entityManager.persist(Like.builder().post(post).user(user).build());

        User user1 = User.builder().name("testuser1").password("credentials1").email("testuser1@mail.se").build();
        User user2 = User.builder().name("testuser2").password("credentials2").email("testuser2@mail.se").build();
        User user3 = User.builder().name("testuser3").password("credentials3").email("testuser3@mail.se").build();

        final Post post2 = entityManager.persist(
                Post.builder().body("test1").interp("test interp1").createdBy(user).build()
        );

        Arrays.asList(user1,user2,user3).stream()
                .map(u -> entityManager.persist(u))
                .map(u -> Like.builder().post(post2).user(u).build())
                .forEach(like -> entityManager.persist(like));

        Long userCount = likeRepo.countByPostId(post2.getId());

        assertThat(userCount,equalTo(Long.valueOf(3)));
    }

    @Test
    public void testCountByUserId() throws Exception {

        final User user = entityManager.persist(
                User.builder().name("user").password("credentials").email("user@mail.se").build()
        );
        final Post post = entityManager.persist(
                Post.builder().body("test").interp("test interp").createdBy(user).build()
        );

        entityManager.persist(Like.builder().user(user).post(post).build());

        Post post1 = Post.builder().body("test1").interp("test interp1").createdBy(user).build();
        Post post2 = Post.builder().body("test1").interp("test interp1").createdBy(user).build();
        Post post3 = Post.builder().body("test1").interp("test interp1").createdBy(user).build();

        final User user2 = entityManager.persist(
                User.builder().name("user2").password("credentials2").email("user2@mail.se").build()
        );

        Arrays.asList(post1,post2,post3).stream()
                .map(p -> entityManager.persist(p))
                .map(p -> Like.builder().post(p).user(user2).build())
                .forEach(like -> entityManager.persist(like));

        Long postCount = likeRepo.countByUserId(user2.getId());

        assertThat(postCount,equalTo(Long.valueOf(3)));
    }
}
