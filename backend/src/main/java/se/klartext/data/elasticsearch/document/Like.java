package se.klartext.data.elasticsearch.document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@NoArgsConstructor
public class Like extends BaseDocument {

    @Getter @Setter
    private User user;

    @Builder
    private Like(String id, LocalDateTime createdAt, User user){
        super(id,createdAt);
        this.user = user;
    }
}
