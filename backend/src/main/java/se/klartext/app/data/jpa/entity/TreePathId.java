package se.klartext.app.data.jpa.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
public class TreePathId implements java.io.Serializable{

    private static final long serialVersionUID = 1L;

    @Column(name = "ancestor_id",nullable = false)
    private Long ancestorId;

    @Column(name = "descendant_id",nullable = false)
    private Long descendantId;


    public TreePathId(Long ancestorId,Long descendantId){
        this.ancestorId = ancestorId;
        this.descendantId = descendantId;
    }
}
