package se.klartext.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by suchuan on 2017-05-21.
 */
@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Post extends BaseEntity {

    private String body;

    private String translation;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;


}
