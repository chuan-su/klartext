package se.klartext.dto.example;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ExampleExtendedDto extends ExampleDto {

    private int totalLikes;

    private int totalComments;

    @Builder
    private ExampleExtendedDto(long id,
                               String body,
                               String translation,
                               int totalLikes,
                               int totalComments,
                               long userId,
                               String userName,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt)
    {
        super(id,body,translation,userId,userName,createdAt,updatedAt);
        this.totalLikes = totalLikes;
        this.totalComments = totalComments;
    }
}
