package se.klartext.domain.model.post;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
public class PostTreePathId implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    @Column(name = "ancestor_id",nullable = false)
    private Long ancestorId;

    @Column(name = "descendant_id",nullable = false)
    private Long descendantId;


    public PostTreePathId(Long ancestorId, Long descendantId){
        this.ancestorId = ancestorId;
        this.descendantId = descendantId;
    }

    @Override
    public boolean equals(Object o){
        return ((o instanceof PostTreePath) &&
            ancestorId == ((PostTreePath)o).getAncestorId()) &&
            descendantId == ((PostTreePath)o).getDescendantId();
    }
    @Override
    public int hashCode(){
        return ancestorId.hashCode() + descendantId.intValue() * 31;
    }
}
