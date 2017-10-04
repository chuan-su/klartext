package se.klartext.app.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@DiscriminatorValue("comment")
public class Comment extends Post {

    @Builder
    public Comment(String body,User createdBy) {
        super(body,createdBy);
    }
}
