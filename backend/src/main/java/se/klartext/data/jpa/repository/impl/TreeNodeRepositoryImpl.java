package se.klartext.data.jpa.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import se.klartext.domain.model.post.Post;
import se.klartext.data.jpa.repository.api.ClosureTable;
import se.klartext.domain.model.post.PostTreePathRepository;
import se.klartext.domain.model.post.PostTreePath_;
import se.klartext.domain.model.post.comment.Comment;
import se.klartext.domain.model.post.PostTreePath;
import se.klartext.domain.model.post.comment.Comment_;
import se.klartext.domain.model.user.User;
import se.klartext.domain.model.user.User_;

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

    private final PostTreePathRepository postTreePathRepository;

    @Autowired
    public TreeNodeRepositoryImpl(PostTreePathRepository postTreePathRepository) {
        this.postTreePathRepository = postTreePathRepository;
    }

    @Override
    @Transactional
    public void addTreeNode(Post ancestor, Post descendant) {
        List<PostTreePath> postTreePaths = new ArrayList<>();
        try(Stream<PostTreePath> stream = postTreePathRepository.findByIdDescendantId(ancestor.getId())){
            stream.map(treePath -> new PostTreePath(treePath.getAncestorId(), descendant.getId(),treePath.getPathLength() + 1))
                    .forEach(postTreePaths::add);
        }
        Optional<PostTreePath> first = postTreePaths.stream().findFirst();

        if(!first.isPresent()){
            postTreePaths.add(new PostTreePath(ancestor,ancestor,0));
            postTreePaths.add(new PostTreePath(ancestor,descendant,1));
        }
        postTreePaths.add(new PostTreePath(descendant,descendant,0));

        postTreePathRepository.save(postTreePaths);
    }

    @Override
    @Transactional
    public long descendantCount(Post ancestor) {
        return postTreePathRepository.count((root, query, cb) ->
            cb.and(cb.equal(root.get(PostTreePath_.ancestor),ancestor.getId()),
                cb.greaterThanOrEqualTo(root.get(PostTreePath_.pathLength),1)));
    }

    @Override
    public List<Object[]> findDescendants(Post ancestor) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> c = cb.createQuery(Object[].class);

        Root<Comment>           commentRoot = c.from(Comment.class);
        Join<Comment,User>      userRoot    = commentRoot.join(Comment_.createdBy);
        Join<Comment,PostTreePath>  treePath    = commentRoot.join(Comment_.descendantPaths);
        Comment co = new Comment();

        c.multiselect(
            commentRoot.get(Comment_.body),
            userRoot.get(User_.name),
            treePath.get(PostTreePath_.pathLength),
            treePath.get(PostTreePath_.descendant)
        ).where(cb.and(
            cb.equal(treePath.get(PostTreePath_.ancestor),ancestor.getId()),
            cb.greaterThanOrEqualTo(treePath.get(PostTreePath_.pathLength),1)));

        return entityManager.createQuery(c).getResultList();
    }
}
