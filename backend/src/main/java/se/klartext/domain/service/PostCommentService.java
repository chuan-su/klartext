package se.klartext.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se.klartext.domain.model.post.Like.Like;
import se.klartext.domain.model.post.Post;
import se.klartext.domain.model.post.comment.Comment;
import se.klartext.domain.model.user.User;

public interface PostCommentService {

    Comment commentOnPost(Post post, Comment comment);

    Long numberOfComments(Post post);

    Page<Comment> getPostComments(Post post, Pageable pageable);
}
