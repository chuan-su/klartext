package se.klartext.app.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@DiscriminatorValue("comment")
@Builder @Getter @Setter
public class Comment extends TreeNode {

    private String body;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
}
