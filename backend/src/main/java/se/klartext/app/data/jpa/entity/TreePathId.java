package se.klartext.app.data.jpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TreePathId implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    @Column(name = "ancestor_id",nullable = false)
    @Getter
    private Long ancestorId;

    @Column(name = "descendant_id",nullable = false)
    @Getter
    private Long descendantId;

    public TreePathId(){}

    public TreePathId(Long ancestorId,Long descendantId){
        this.ancestorId = ancestorId;
        this.descendantId = descendantId;
    }
    @Override
    public boolean equals(Object o){
        return ((o instanceof TreePathId) &&
                ancestorId == ((TreePathId)o).getAncestorId()) &&
                descendantId == ((TreePathId)o).getDescendantId();
    }
    @Override
    public int hashCode(){
        return ancestorId.hashCode() + descendantId.intValue() * 31;
    }
}
