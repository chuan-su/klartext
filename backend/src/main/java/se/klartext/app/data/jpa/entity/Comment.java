package se.klartext.app.data.jpa.entity;


import lombok.Builder;

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
