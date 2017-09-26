package se.klartext.app.data.impl.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import se.klartext.app.data.api.ClosureTable;
import se.klartext.app.data.api.jpa.TreePathRepository;
import se.klartext.app.model.Comment;
import se.klartext.app.model.TreeNode;
import se.klartext.app.model.TreePath;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TreeNodeRepositoryImpl implements ClosureTable<TreeNode> {

    @PersistenceContext
    private EntityManager entityManager;

    private final TreePathRepository<TreePath> treePathRepository;

    @Autowired
    public TreeNodeRepositoryImpl(TreePathRepository treePathRepository) {
        this.treePathRepository = treePathRepository;
    }

    @Override
    @Transactional
    public void addTreeNode(TreeNode ancestor, TreeNode descendant) {
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
    public long descendantCount(TreeNode ancestor) {
        return treePathRepository.count((root,query,cb) ->
                        cb.and(cb.equal(root.get("ancestor"),ancestor.getId()),
                        cb.greaterThanOrEqualTo(root.get("pathLength"),1)));
    }

    @Override
    public List<Object[]> findDescendants(TreeNode ancestor) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> c = cb.createQuery(Object[].class);

        Root<Comment> commentRoot = c.from(Comment.class);
        Join<TreeNode,TreePath> treePath = commentRoot.join("descendantPaths");

        c.multiselect(commentRoot.get("body"),treePath.get("pathLength"),commentRoot.get("id"),treePath.get("descendant"))
                .where(cb.and(cb.equal(treePath.get("ancestor"),ancestor.getId())),
                        cb.greaterThanOrEqualTo(treePath.get("pathLength"),1));

        return entityManager.createQuery(c).getResultList();
    }
}
