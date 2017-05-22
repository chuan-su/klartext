package se.klartext.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;
import lombok.experimental.Tolerate;

import javax.persistence.*;

/**
 * Created by suchuan on 2017-05-21.
 */
@Entity
@Table(name = "posts")
@EntityListeners(PostListener.class)
@Builder

public class Post extends BaseEntity {

    @Getter @Setter
    private String body;

    @Getter @Setter
    private String translation;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @Getter
    @JsonIgnore
    private User createdBy;

    @Tolerate
    public Post(){}
}
