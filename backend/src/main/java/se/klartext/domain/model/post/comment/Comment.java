package se.klartext.domain.model.post.comment;


import lombok.*;
import se.klartext.domain.model.post.Post;
import se.klartext.domain.model.post.PostThread;
import se.klartext.domain.model.user.User;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@DiscriminatorValue("comment")
@NoArgsConstructor
@Data
public class Comment extends Post {

    @ManyToOne
    @JoinColumn(name = "thread_id")
    private PostThread postThread;

    @Builder
    public Comment(PostThread postThread, String body,User createdBy) {
        super(body,createdBy);
        this.postThread = postThread;
    }

}
