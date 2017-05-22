package se.klartext.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

/**
 * Created by suchuan on 2017-05-21.
 */
@Entity
@Table(name = "posts")
@EntityListeners(PostListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Post extends BaseEntity {

    @Setter
    private String body;

    @Setter
    private String translation;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;


}
