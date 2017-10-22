package se.klartext.app.data.jpa.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Created by suchuan on 2017-05-21.
 */
@Entity
@Table(name = "examples")
@DiscriminatorValue("example")
@Data
@NoArgsConstructor
public class Example extends Post {

    private String translation;

    @Builder
    public Example(String body, String translation, User createdBy) {
        super(body,createdBy);
        this.translation = translation;
    }
}
