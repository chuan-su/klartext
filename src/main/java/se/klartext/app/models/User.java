package se.klartext.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suchuan on 2017-05-20.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "users")
public class User extends BaseEntity{

    private String name,email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private  String password;

    @OneToMany(mappedBy = "createdBy")
    @JsonIgnore
    private List<Post> posts = new ArrayList<>();
}
