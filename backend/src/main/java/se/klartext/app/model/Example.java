package se.klartext.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by suchuan on 2017-05-21.
 */
@Entity
@Table(name = "examples")
@DiscriminatorValue("example")
@Builder @Getter @Setter
public class Example extends TreeNode {

    private String body;

    private String interp;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    @OneToMany(mappedBy = "example",orphanRemoval = true)
    @JsonIgnore
    private Set<Like> likes = new HashSet<>();
}
