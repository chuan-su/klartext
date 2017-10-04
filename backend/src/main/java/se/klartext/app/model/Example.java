package se.klartext.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by suchuan on 2017-05-21.
 */
@Entity
@Table(name = "examples")
@DiscriminatorValue("example")
public class Example extends Post {

    @Getter @Setter
    private String interp;

    public Example() {}

    @Builder
    public Example(String body, String interp, User createdBy) {
        super(body,createdBy);
        this.interp = interp;
    }
}
