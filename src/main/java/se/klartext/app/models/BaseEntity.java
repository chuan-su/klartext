package se.klartext.app.models;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Created by suchuan on 2017-05-20.
 */
@MappedSuperclass
@Getter
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    Long id;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

}
