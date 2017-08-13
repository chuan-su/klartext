package se.klartext.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by suchuan on 2017-05-21.
 */
@Entity
@Table(name = "posts")
@Builder
@Getter
@Setter
public class Post extends BaseEntity {

    private String body;

    private String interp;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE,orphanRemoval = false)
    private Set<Like> likes = new HashSet<>();

    @Tolerate
    public Post(){}
}
