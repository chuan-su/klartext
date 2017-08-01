package se.klartext.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suchuan on 2017-05-20.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "users")
public class User extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private  String password;

    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    @Setter
    @JsonIgnore
    private AuthToken authToken;
}
