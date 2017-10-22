package se.klartext.app.data.jpa.repository;

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
import se.klartext.app.data.jpa.entity.Example;
import se.klartext.app.data.jpa.entity.Like;
import se.klartext.app.data.jpa.entity.User;
import se.klartext.app.data.jpa.repository.api.LikeRepository;

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

        Example example = Example.builder().body("test").translation("test interp").createdBy(user).build();
        example = entityManager.persist(example);

        Like like = Like.builder().user(user).post(example).build();
        Like likeEntity = likeRepo.save(like);

        assertThat(likeEntity.getId(),is(notNullValue()));
        assertThat(likeEntity.getCreatedAt(),is(notNullValue()));
        assertThat(likeEntity.getUpdatedAt(),is(notNullValue()));
        assertThat(likeEntity.getPost().getId(),is(example.getId()));
        assertThat(likeEntity.getUser().getId(),is(user.getId()));
    }
    @Test
    public void testFindByUserIdAndPostId() throws Exception {
        User user = User.builder().name("chuan").password("credentials").email("chuan@mail.se").build();
        user = entityManager.persist(user);

        Example example = Example.builder().body("test").translation("test interp").createdBy(user).build();
        example = entityManager.persist(example);

        Like like = Like.builder().user(user).post(example).build();
        like = entityManager.persist(like);

        Optional<Like> likeEntity = likeRepo.findByUserIdAndPostId(user.getId(), example.getId());
        assertTrue(likeEntity.isPresent());
        assertThat(likeEntity.get().getPost().getBody(),is(example.getBody()));
        assertThat(likeEntity.get().getUser().getEmail(),is(user.getEmail()));
    }
    @Test
    public void testFindByPostId() throws Exception {

        final User user = entityManager.persist(
                User.builder().name("chuan").password("credentials").email("chuan@mail.se").build()
        );

        final Example example = entityManager.persist(
                Example.builder().body("test1").translation("test interp1").createdBy(user).build()
        );

        User user1 = User.builder().name("testuser1").password("credentials1").email("testuser1@mail.se").build();
        User user2 = User.builder().name("testuser2").password("credentials2").email("testuser2@mail.se").build();
        User user3 = User.builder().name("testuser3").password("credentials3").email("testuser3@mail.se").build();

        Arrays.asList(user1,user2,user3).stream()
                .map(u -> entityManager.persist(u))
                .map(u -> Like.builder().post(example).user(u).build())
                .forEach(like -> entityManager.persist(like));

        Pageable pageable = new PageRequest(0,10);

        Page<Like> likes = likeRepo.findByPostId(example.getId(),pageable);
        assertThat(likes,hasProperty("content"));
        assertThat(likes.getTotalElements(),equalTo(Long.valueOf(3)));
    }

    @Test
    public void testCountByPostId() throws Exception {

        final User user = entityManager.persist(
                User.builder().name("user").password("credentials").email("user@mail.se").build()
        );

        final Example example = entityManager.persist(
                Example.builder().body("test1").translation("test interp1").createdBy(user).build()
        );

        entityManager.persist(Like.builder().post(example).user(user).build());

        User user1 = User.builder().name("testuser1").password("credentials1").email("testuser1@mail.se").build();
        User user2 = User.builder().name("testuser2").password("credentials2").email("testuser2@mail.se").build();
        User user3 = User.builder().name("testuser3").password("credentials3").email("testuser3@mail.se").build();

        final Example example2 = entityManager.persist(
                Example.builder().body("test1").translation("test interp1").createdBy(user).build()
        );

        Arrays.asList(user1,user2,user3).stream()
                .map(u -> entityManager.persist(u))
                .map(u -> Like.builder().post(example2).user(u).build())
                .forEach(like -> entityManager.persist(like));

        Long userCount = likeRepo.countByPostId(example2.getId());

        assertThat(userCount,equalTo(Long.valueOf(3)));
    }

    @Test
    public void testCountByUserId() throws Exception {

        final User user = entityManager.persist(
                User.builder().name("user").password("credentials").email("user@mail.se").build()
        );
        final Example example = entityManager.persist(
                Example.builder().body("test").translation("test interp").createdBy(user).build()
        );

        entityManager.persist(Like.builder().user(user).post(example).build());

        Example example1 = Example.builder().body("test1").translation("test interp1").createdBy(user).build();
        Example example2 = Example.builder().body("test1").translation("test interp1").createdBy(user).build();
        Example example3 = Example.builder().body("test1").translation("test interp1").createdBy(user).build();

        final User user2 = entityManager.persist(
                User.builder().name("user2").password("credentials2").email("user2@mail.se").build()
        );

        Arrays.asList(example1, example2, example3).stream()
                .map(p -> entityManager.persist(p))
                .map(p -> Like.builder().post(p).user(user2).build())
                .forEach(like -> entityManager.persist(like));

        Long postCount = likeRepo.countByUserId(user2.getId());

        assertThat(postCount,equalTo(Long.valueOf(3)));
    }
}
