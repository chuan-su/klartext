package se.klartext.app.data.elasticsearch.document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@NoArgsConstructor
public class User extends BaseDocument {

    @Getter @Setter
    private String name;

    @Builder
    private User(String id, String name, LocalDateTime createdAt){
        super(id,createdAt);
        this.name = name;
    }
}
