package se.klartext.app.model.elasticsearch;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class UserDocument extends BaseDocument {

    @Getter @Setter
    private String name;

    @Builder
    private UserDocument(String id, String name, LocalDateTime createdAt){
        super(id,createdAt);
        this.name = name;
    }
}
