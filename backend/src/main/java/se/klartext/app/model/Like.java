package se.klartext.app.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "likes")
@Builder @Getter @Setter
public class Like extends BaseEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name="post_id",referencedColumnName = "id")
    private Post example;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

}
