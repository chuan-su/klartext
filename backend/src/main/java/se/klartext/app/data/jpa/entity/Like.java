package se.klartext.app.data.jpa.entity;

import lombok.*;

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
