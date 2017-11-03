package se.klartext.domain.model.post.Like;

import lombok.*;
import se.klartext.domain.shared.BaseEntity;
import se.klartext.domain.model.post.Post;
import se.klartext.domain.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "likes")
@Data
@NoArgsConstructor
public class Like extends BaseEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name="post_id",referencedColumnName = "id")
    private Post post;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @Builder
    public Like(Post post, User user){
        this.post = post;
        this.user = user;
    }
}
