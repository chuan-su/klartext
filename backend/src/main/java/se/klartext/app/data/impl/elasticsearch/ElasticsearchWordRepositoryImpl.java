package se.klartext.app.data.impl.elasticsearch;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Single;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Repository;
import se.klartext.app.data.api.elasticsearch.ElasticsearchWordRepository;
import se.klartext.app.model.elasticsearch.WordDocument;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Repository
public class ElasticsearchWordRepositoryImpl extends ElasticsearchBaseRepositoryImpl<WordDocument>
        implements ElasticsearchWordRepository{

    @Override
    public Single<List<WordDocument>> findWordMatch(String query) {
        QueryBuilder multiMathQuery = multiMatchQuery(query, "value","inflection","translation");

        return this.find(multiMathQuery)
                .map(source -> new ObjectMapper().convertValue(source,WordDocument.class))
                .collect(ArrayList::new,List::add);
    }

    @Override
    public String getDocumentType() {
        return "word";
    }
}
