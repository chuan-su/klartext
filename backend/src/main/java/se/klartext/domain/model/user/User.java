package se.klartext.domain.model.user;

import lombok.*;
import se.klartext.domain.shared.BaseEntity;
import se.klartext.domain.model.post.Like.Like;
import se.klartext.domain.model.post.Post;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by suchuan on 2017-05-20.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private  String password;

    @OneToOne(mappedBy = "user",orphanRemoval = true)
    private AuthToken authToken;

    @OneToMany(mappedBy = "createdBy",orphanRemoval = true)
    private final List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user",orphanRemoval = true)
    private final Set<Like> likes = new HashSet<>();

    @Builder
    public User(String name,String email,String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
