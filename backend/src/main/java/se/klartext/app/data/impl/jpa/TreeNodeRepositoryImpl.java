package se.klartext.app.data.impl.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import se.klartext.app.data.api.ClosureTable;
import se.klartext.app.data.api.jpa.TreePathRepository;
import se.klartext.app.model.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TreeNodeRepositoryImpl implements ClosureTable<Post> {

    @PersistenceContext
    private EntityManager entityManager;

    private final TreePathRepository<TreePath> treePathRepository;

    @Autowired
    public TreeNodeRepositoryImpl(TreePathRepository treePathRepository) {
        this.treePathRepository = treePathRepository;
    }

    @Override
    @Transactional
    public void addTreeNode(Post ancestor, Post descendant) {
        List<TreePath> treePaths = new ArrayList<>();
        try(Stream<TreePath> stream = treePathRepository.findByIdDescendantId(ancestor.getId())){
            stream.map(treePath -> new TreePath(treePath.getAncestorId(), descendant.getId(),treePath.getPathLength() + 1))
                    .forEach(treePaths::add);
        }
        Optional<TreePath> first = treePaths.stream().findFirst();

        if(!first.isPresent()){
            treePaths.add(new TreePath(ancestor,ancestor,0));
            treePaths.add(new TreePath(ancestor,descendant,1));
        }
        treePaths.add(new TreePath(descendant,descendant,0));

        treePathRepository.save(treePaths);
    }

    @Override
    @Transactional
    public long descendantCount(Post ancestor) {
        return treePathRepository.count((root,query,cb) ->
                        cb.and(cb.equal(root.get(TreePath_.ancestor),ancestor.getId()),
                        cb.greaterThanOrEqualTo(root.get(TreePath_.pathLength),1)));
    }

    @Override
    public List<Object[]> findDescendants(Post ancestor) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> c = cb.createQuery(Object[].class);

        Root<Comment>           commentRoot = c.from(Comment.class);
        Join<Comment,User>      userRoot    = commentRoot.join(Comment_.createdBy);
        Join<Comment,TreePath>  treePath    = commentRoot.join(Comment_.descendantPaths);

        c.multiselect(
            commentRoot.get(Comment_.body),
            userRoot.get(User_.name),
            treePath.get(TreePath_.pathLength),
            treePath.get(TreePath_.descendant)
        ).where(cb.and(
            cb.equal(treePath.get(TreePath_.ancestor),ancestor.getId()),
            cb.greaterThanOrEqualTo(treePath.get(TreePath_.pathLength),1)));

        return entityManager.createQuery(c).getResultList();
    }
}
