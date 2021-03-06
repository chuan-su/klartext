package se.klartext.data.elasticsearch.document;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Word extends BaseDocument {

    private String value;

    private String klass;

    private String lang;

    private List<String> translation;

    private List<String> inflection;

    @Builder
    private Word(String id, LocalDateTime createdAt) {
        super(id, createdAt);
    }

}
