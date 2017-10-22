package se.klartext.app.data.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@Data
@NoArgsConstructor
public abstract class Post extends BaseEntity{

    private String body;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "post",orphanRemoval = true)
    private final Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "ancestor", orphanRemoval = true)
    private final List<TreePath> ancestorPaths = new ArrayList<>();

    @OneToMany(mappedBy = "descendant",orphanRemoval = true)
    private final List<TreePath> descendantPaths = new ArrayList<>();

    public Post(String body,User createdBy) {
        this.body = body;
        this.createdBy = createdBy;
    }
}
