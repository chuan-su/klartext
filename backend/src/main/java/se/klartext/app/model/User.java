package se.klartext.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
@Builder @Getter @Setter
public class User extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    @JsonIgnore
    private  String password;

    @OneToMany(mappedBy = "createdBy",orphanRemoval = true)
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user",orphanRemoval = true)
    @JsonIgnore
    private Set<Like> likes = new HashSet<>();

    @OneToOne(mappedBy = "user",orphanRemoval = true)
    @JsonIgnore
    private AuthToken authToken;
}
