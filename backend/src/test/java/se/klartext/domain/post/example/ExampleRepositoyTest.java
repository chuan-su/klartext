package se.klartext.domain.post.example;

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
import se.klartext.config.PersistenceConfig;
import se.klartext.domain.model.post.comment.Comment;
import se.klartext.domain.model.post.comment.CommentRepository;
import se.klartext.domain.model.post.example.Example;
import se.klartext.domain.model.user.User;
import se.klartext.domain.model.post.example.ExampleRepository;

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
public class ExampleRepositoyTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ExampleRepository repository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void testAddComment() throws Exception {
        User user = User.builder().name("chuan").password("credentials").email("chuan@mail.se").build();
        user = entityManager.persist(user);

        Example example = Example.builder().body("test").translation("test interp").createdBy(user).build();
        example = entityManager.persist(example);
        Comment comment = entityManager.persist(new Comment("test comment",user));
        comment.addToPost(example);

       //example.addUserLike(user);
       // comment.addUserLike(user);
        commentRepository.save(comment);
        Comment comment1 = entityManager.persist(new Comment("test comment",user));
        comment1.addToPost(comment);
        commentRepository.save(comment1);
    }

    @Test
    public void testSave() throws Exception{
        User user = User.builder().name("chuan").password("credentials").email("chuan@mail.se").build();
        user = entityManager.persist(user);

        Example example = Example.builder().body("test").translation("test interp").createdBy(user).build();
        Example exampleEntity = repository.save(example);
        assertThat(exampleEntity.getId(),is(notNullValue()));
        assertThat(exampleEntity.getCreatedAt(),is(notNullValue()));
        assertThat(exampleEntity.getUpdatedAt(),is(notNullValue()));
    }
    @Test
    public void testFindOne() throws Exception{
        User user = User.builder().name("test user").email("test@mail.com").password("credentials").build();
        Example example = Example.builder().body("test body").translation("test body interp").build();
        user = entityManager.persist(user);
        example.setCreatedBy(user);
        example =entityManager.persist(example);
        assertTrue(repository.findOne(example.getId()).isPresent());
        assertThat(repository.findOne(example.getId()).get(),hasProperty("id",is(example.getId())));
    }

    @Test
    public void testDelete() throws Exception{
        User user = User.builder().name("test user").email("test@mail.com").password("credentials").build();
        Example example = Example.builder().body("test body").translation("test body interp").build();
        user = entityManager.persist(user);
        example.setCreatedBy(user);
        example =entityManager.persist(example);
        repository.delete(example);
        assertFalse(repository.findOne(example.getId()).isPresent());
    }

    @Test
    public void testFindByCreatedById() throws Exception{
        User user1 = User.builder().name("test user1").email("test1@mail.com").password("credentials1").build();
        User user2 = User.builder().name("test user2").email("test2@mail.com").password("credentials2").build();
        User user3 = User.builder().name("test user3").email("test3@mail.com").password("credentials3").build();

        user1 = entityManager.persist(user1);
        user2 = entityManager.persist(user2);
        user3 = entityManager.persist(user3);

        Example example1 = Example.builder().body("test body1").translation("test body interp1").createdBy(user1).build();
        Example example2 = Example.builder().body("test body2").translation("test body interp2").createdBy(user1).build();
        Example example3 = Example.builder().body("test body3").translation("test body interp3").createdBy(user2).build();
        Example example4 = Example.builder().body("test body4").translation("test body interp4").createdBy(user3).build();

        Arrays.asList(example1, example2, example3, example4).stream().forEach(post -> repository.save(post));
        Pageable pageable = new PageRequest(0,10);
        final Long userId = user1.getId();
        Page<Example> page = repository.findByCreatedById(userId,pageable);

        assertEquals(page.getContent().size(),2);
        page.getContent().forEach(p -> {
            Long createdById = Long.valueOf(p.getCreatedBy().getId());
            assertEquals(createdById,userId);
        });
    }
}
