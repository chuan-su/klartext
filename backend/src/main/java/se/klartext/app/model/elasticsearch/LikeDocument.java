package se.klartext.app.model.elasticsearch;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class LikeDocument extends BaseDocument {

    @Getter @Setter
    private UserDocument user;

    @Builder
    private LikeDocument(String id, LocalDateTime createdAt,UserDocument userDocument){
        super(id,createdAt);
        this.user = userDocument;
    }
}
