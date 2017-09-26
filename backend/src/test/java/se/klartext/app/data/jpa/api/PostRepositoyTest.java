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
import se.klartext.app.data.api.jpa.PostRepository;
import se.klartext.app.model.Post;
import se.klartext.app.model.User;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Import(PersistenceConfig.class)
@DataJpaTest
public class PostRepositoyTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository repository;

    @Test
    public void testSave() throws Exception{
        User user = User.builder().name("chuan").password("credentials").email("chuan@mail.se").build();
        user = entityManager.persist(user);

        Post post = Post.builder().body("test").interp("test interp").createdBy(user).build();
        Post postEntity = repository.save(post);
        assertThat(postEntity.getId(),is(notNullValue()));
        assertThat(postEntity.getCreatedAt(),is(notNullValue()));
        assertThat(postEntity.getUpdatedAt(),is(notNullValue()));
    }
    @Test
    public void testFindOne() throws Exception{
        User user = User.builder().name("test user").email("test@mail.com").password("credentials").build();
        Post post = Post.builder().body("test body").interp("test body interp").build();
        user = entityManager.persist(user);
        post.setCreatedBy(user);
        post =entityManager.persist(post);
        assertTrue(repository.findOne(post.getId()).isPresent());
        assertThat(repository.findOne(post.getId()).get(),hasProperty("id",is(post.getId())));
    }

    @Test
    public void testDelete() throws Exception{
        User user = User.builder().name("test user").email("test@mail.com").password("credentials").build();
        Post post = Post.builder().body("test body").interp("test body interp").build();
        user = entityManager.persist(user);
        post.setCreatedBy(user);
        post =entityManager.persist(post);
        repository.delete(post);
        assertFalse(repository.findOne(post.getId()).isPresent());
    }

    @Test
    public void testFindByCreatedById() throws Exception{
        User user1 = User.builder().name("test user1").email("test1@mail.com").password("credentials1").build();
        User user2 = User.builder().name("test user2").email("test2@mail.com").password("credentials2").build();
        User user3 = User.builder().name("test user3").email("test3@mail.com").password("credentials3").build();

        user1 = entityManager.persist(user1);
        user2 = entityManager.persist(user2);
        user3 = entityManager.persist(user3);

        Post post1 = Post.builder().body("test body1").interp("test body interp1").createdBy(user1).build();
        Post post2 = Post.builder().body("test body2").interp("test body interp2").createdBy(user1).build();
        Post post3 = Post.builder().body("test body3").interp("test body interp3").createdBy(user2).build();
        Post post4 = Post.builder().body("test body4").interp("test body interp4").createdBy(user3).build();

        Arrays.asList(post1,post2,post3,post4).stream().forEach(post -> repository.save(post));
        Pageable pageable = new PageRequest(0,10);
        final Long userId = user1.getId();
        Page<Post> page = repository.findByCreatedById(userId,pageable);

        assertEquals(page.getContent().size(),2);
        page.getContent().forEach(p -> {
            Long createdById = Long.valueOf(p.getCreatedBy().getId());
            assertEquals(createdById,userId);
        });
    }
}
