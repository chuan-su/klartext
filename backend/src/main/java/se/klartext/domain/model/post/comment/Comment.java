package se.klartext.domain.model.post.comment;


import lombok.*;
import org.springframework.data.annotation.Transient;
import se.klartext.domain.model.post.Post;
import se.klartext.domain.model.post.PostTreePath;
import se.klartext.domain.model.user.User;

import javax.persistence.*;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Entity
@Table(name = "comments")
@DiscriminatorValue("comment")
@NoArgsConstructor
@Data
public class Comment extends Post {

    @Builder
    public Comment(String body,User createdBy) {
        super(body,createdBy);
    }

    public void addToPost(Post post) {
        List<PostTreePath> postTreePaths = post.getAncestorPaths().stream()
            .peek(path -> System.out.println(path))
            .map(path -> new PostTreePath(path.getAncestorId(),this.getId(),path.getPathLength() + 1))
            .collect(toList());

        if(postTreePaths.size() == 0) {
//            this.getAncestorPaths().add(new PostTreePath(post.getId(),post.getId(),0));
            this.getAncestorPaths().add(new PostTreePath(post.getId(),this.getId(),1));
        }else{
            this.getAncestorPaths().addAll(postTreePaths);
        }
        this.getAncestorPaths().add(new PostTreePath(this.getId(),this.getId(),0));
    }
}
