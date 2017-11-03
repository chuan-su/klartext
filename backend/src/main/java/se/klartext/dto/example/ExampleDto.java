package se.klartext.dto.example;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.klartext.lib.serializer.LocalDateTimeSerializer;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExampleDto {

    private long id;

    private String body;

    private String translation;

    private long userId;

    private String userName;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;
}
