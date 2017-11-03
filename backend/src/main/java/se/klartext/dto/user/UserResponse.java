package se.klartext.dto.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import se.klartext.lib.serializer.LocalDateTimeSerializer;
import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {

    private long id;

    private String name;

    private String email;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;

}
