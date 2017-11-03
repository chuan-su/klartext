package se.klartext.domain.model.post;

import lombok.Data;
import lombok.NoArgsConstructor;
import se.klartext.domain.model.post.comment.Comment;
import se.klartext.domain.shared.BaseEntity;
import se.klartext.domain.model.post.Like.Like;
import se.klartext.domain.model.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "posts")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "post_type",discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
public abstract class Post extends BaseEntity {

    private String body;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "ancestor", orphanRemoval = true, cascade = CascadeType.ALL)
    private final Set<PostTreePath> ancestorPaths = new HashSet<>();

    @OneToMany(mappedBy = "descendant",orphanRemoval = true, cascade = CascadeType.ALL)
    private final Set<PostTreePath> descendantPaths = new HashSet<>();

    public Post(String body,User createdBy) {
        this.body = body;
        this.createdBy = createdBy;
    }

    public void addUserLike(User user) {
        Like like = new Like(this,user);
        this.likes.add(like);
    }

    public void removeUserLike(User user) {
        Like like = new Like(this,user);
        this.likes.remove(like);
    }
}
