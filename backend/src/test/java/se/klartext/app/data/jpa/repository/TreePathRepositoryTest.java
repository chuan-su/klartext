package se.klartext.app.data.jpa.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import se.klartext.app.data.jpa.entity.*;
import se.klartext.app.data.jpa.repository.api.TreePathRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TreePathRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    TreePathRepository repo;

    private Post postTreeNode;
    private Post commentTreeNode;

    @Before
    public void setUp() throws Exception {
        Example example = entityManager.persist(
                Example.builder().body("example data").build());

        Comment comment = entityManager.persist(
                Comment.builder().body("comment data").build());

        postTreeNode = entityManager.persist(example);

        commentTreeNode = entityManager.persist(comment);
    }

    @Test
    public void testFindOne() throws Exception {
        entityManager.persist(new TreePath(postTreeNode,commentTreeNode,1));
        Optional<TreePath> treePath = repo.findOne(new TreePathId(postTreeNode.getId(),commentTreeNode.getId()));
        assertTrue(treePath.isPresent());
        assertEquals(treePath.get().getAncestorId(),postTreeNode.getId());
        assertEquals(treePath.get().getDescendantId(),commentTreeNode.getId());
        assertEquals(treePath.get().getPathLength(),1);
    }

}
