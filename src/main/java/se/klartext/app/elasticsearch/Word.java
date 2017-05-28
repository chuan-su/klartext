package se.klartext.app.elasticsearch;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.lang.annotation.Documented;
import java.util.List;

/**
 * Created by suchuan on 2017-05-28.
 */
@Document(indexName = "klartext",type = "word")
@Setter
@Getter
public class Word {
    @Id
    private String id;

    private String value;

    private String klass, lang;

    private List<String> inflection,translation;
}
