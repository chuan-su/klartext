package se.klartext.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;

/**
 * Created by suchuan on 2017-05-21.
 */
@Entity
@Table(name = "posts")
@Builder
public class Post extends BaseEntity {

    @Getter @Setter
    private String body;

    @Getter @Setter
    private String interp;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @Getter
    @JsonIgnore
    private User createdBy;

    @Tolerate
    public Post(){}
}
