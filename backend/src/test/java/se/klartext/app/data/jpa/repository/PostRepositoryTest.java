package se.klartext.app.data.jpa.repository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import se.klartext.app.data.jpa.repository.api.TreeNodeRepository;
import se.klartext.app.data.jpa.entity.*;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    TreeNodeRepository repo;


    private Post postTreeNode;
    private Post commentTreeNode;

    @Before
    public void setUp() throws Exception {
        User user = entityManager.persist(User.builder().email("chuansu@mail.com").name("chuan su").password("credentials").build());
        postTreeNode = entityManager.persist(Example.builder().body("example data").createdBy(user).build());
        commentTreeNode = entityManager.persist(Comment.builder().body("comment data").createdBy(user).build());
    }

    @Test
    public void testAddTreeNode() throws Exception {
        repo.addTreeNode(postTreeNode,commentTreeNode);

        assertNotNull(entityManager.find(
                TreePath.class,
                new TreePathId(postTreeNode.getId(),postTreeNode.getId())));

        assertNotNull(entityManager.find(
                TreePath.class,
                new TreePathId(postTreeNode.getId(),commentTreeNode.getId())));

        assertNotNull(entityManager.find(
                TreePath.class,
                new TreePathId(commentTreeNode.getId(),commentTreeNode.getId())));
    }

    @Test
    public void getDescendantCount() throws Exception {
        repo.addTreeNode(postTreeNode,commentTreeNode);
        long count = repo.descendantCount(postTreeNode);
        assertEquals(1,count);
    }

    @Test
    public void testFindDescendants() throws Exception {
        repo.addTreeNode(postTreeNode,commentTreeNode);
        List<Object[]> list = repo.findDescendants(postTreeNode);
        System.out.println(list);
    }
}
