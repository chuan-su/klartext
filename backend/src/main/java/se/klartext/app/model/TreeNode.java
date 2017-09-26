package se.klartext.app.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tree_nodes")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tree_node_type",discriminatorType = DiscriminatorType.STRING)
@Getter @Setter
public abstract class TreeNode extends BaseEntity{

    @OneToMany(mappedBy = "ancestor", orphanRemoval = true)
    private List<TreePath> ancestorPaths = new ArrayList<>();

    @OneToMany(mappedBy = "descendant",orphanRemoval = true)
    private List<TreePath> descendantPaths = new ArrayList<>();
}
