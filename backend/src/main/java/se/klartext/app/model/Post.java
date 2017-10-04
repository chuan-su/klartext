package se.klartext.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tree_nodes")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tree_node_type",discriminatorType = DiscriminatorType.STRING)
@Getter @Setter
public abstract class Post extends BaseEntity{

    private String body;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    @OneToMany(mappedBy = "example",orphanRemoval = true)
    @JsonIgnore
    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "ancestor", orphanRemoval = true)
    private List<TreePath> ancestorPaths = new ArrayList<>();

    @OneToMany(mappedBy = "descendant",orphanRemoval = true)
    private List<TreePath> descendantPaths = new ArrayList<>();

    public Post() {}

    public Post(String body,User createdBy) {
        this.body = body;
        this.createdBy = createdBy;
    }
}
