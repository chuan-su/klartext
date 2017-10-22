package se.klartext.app.data.jpa.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tree_paths")
@Data
@NoArgsConstructor
public class TreePath {

    @EmbeddedId
    private TreePathId id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ancestor_id",nullable = false,insertable = false, updatable = false)
    private Post ancestor;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "descendant_id",nullable = false,insertable = false, updatable = false)
    private Post descendant;

    private int pathLength;

    public TreePath(TreePathId id,int pathLength){
        this.id = id;
        this.pathLength = pathLength;
    }

    public TreePath(Long ancestorId, Long descendantId, int pathLength){
        this(new TreePathId(ancestorId,descendantId),pathLength);
    }

    public TreePath(Post ancestor, Post descendant, int pathLength){
        this(ancestor.getId(),descendant.getId(),pathLength);
    }

    public Long getAncestorId(){
        return id.getAncestorId();
    }

    public Long getDescendantId(){
        return id.getDescendantId();
    }
}
