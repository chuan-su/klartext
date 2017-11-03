package se.klartext.domain.model.post;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tree_paths")
@Data
@NoArgsConstructor
public class PostTreePath {

    @EmbeddedId
    private PostTreePathId id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ancestor_id",nullable = false,insertable = false, updatable = false)
    private Post descendant;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "descendant_id",nullable = false,insertable = false, updatable = false)
    private Post ancestor;

    private int pathLength;

    public PostTreePath(PostTreePathId id, int pathLength){
        this.id = id;
        this.pathLength = pathLength;
    }

    public PostTreePath(Long ancestorId, Long descendantId, int pathLength){
        this(new PostTreePathId(ancestorId,descendantId),pathLength);
    }

    public PostTreePath(Post ancestor, Post descendant, int pathLength){
        this(ancestor.getId(),descendant.getId(),pathLength);
    }

    public Long getAncestorId(){
        return id.getAncestorId();
    }

    public Long getDescendantId(){
        return id.getDescendantId();
    }
}
