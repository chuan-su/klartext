package se.klartext.app.data.elasticsearch.document;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WordDocument extends BaseDocument {

    private String value;

    private String klass;

    private String lang;

    private List<String> translation;

    private List<String> inflection;

    @Builder
    private WordDocument(String id, LocalDateTime createdAt) {
        super(id, createdAt);
    }

}
