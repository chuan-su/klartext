package se.klartext.app.model.elasticsearch;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import se.klartext.app.lib.serializer.LocalDateTimeDeserializer;
import se.klartext.app.lib.serializer.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostDocument extends BaseDocument{

    @Setter
    private String body;

    @Setter
    private String interp;

    private int totalLikes;

    @Setter
    private List<LikeDocument> likes;

    @Setter
    private UserDocument createdBy;

    @Setter
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;

    @Builder
    private PostDocument(String id,LocalDateTime createdAt,String body,String interp,List<LikeDocument> likes,UserDocument createdBy,LocalDateTime updatedAt ){
        super(id,createdAt);
        this.body = body;
        this.interp = interp;
        this.likes = likes;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.totalLikes = (int)this.likes.stream().count();
    }
}
