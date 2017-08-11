package se.klartext.app.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import se.klartext.app.lib.serializer.LocalDateTimeSerializer;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * Created by suchuan on 2017-05-20.
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    Long id;

    @CreatedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    LocalDateTime createdAt;

    @LastModifiedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    LocalDateTime updatedAt;

}
