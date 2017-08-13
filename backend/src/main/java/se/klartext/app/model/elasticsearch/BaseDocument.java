package se.klartext.app.model.elasticsearch;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.klartext.app.lib.serializer.LocalDateTimeSerializer;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter
@Setter
public abstract class BaseDocument{

    private String id;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    public BaseDocument(String id, LocalDateTime createdAt){
        this.id = id;
        this.createdAt = createdAt;
    }
}
