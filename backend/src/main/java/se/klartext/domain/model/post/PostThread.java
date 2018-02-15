package se.klartext.domain.model.post;

import se.klartext.domain.model.post.comment.Comment;
import se.klartext.domain.shared.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;

@Entity
public class PostThread extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @OneToMany(mappedBy = "comment")
    private HashSet<Comment> comments = new HashSet<>();
}
