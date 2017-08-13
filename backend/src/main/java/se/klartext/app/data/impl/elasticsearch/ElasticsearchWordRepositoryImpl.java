package se.klartext.app.data.impl.elasticsearch;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Observable;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Repository;
import se.klartext.app.data.api.elasticsearch.ElasticsearchWordRepository;
import se.klartext.app.model.elasticsearch.WordDocument;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Repository
public class ElasticsearchWordRepositoryImpl extends ElasticsearchBaseRepositoryImpl<WordDocument>
        implements ElasticsearchWordRepository{

    @Override
    public Observable<List<WordDocument>> findWordMatch(String query) {
        QueryBuilder multiMathQuery = multiMatchQuery(query, "value","inflection","translation");

        return this.find(multiMathQuery)
                .map(source -> new ObjectMapper().convertValue(source,WordDocument.class))
                .toList().toObservable();
    }

    @Override
    public String getDocumentType() {
        return "word";
    }
}
