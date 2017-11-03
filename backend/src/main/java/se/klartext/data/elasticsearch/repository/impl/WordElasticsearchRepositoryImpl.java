package se.klartext.data.elasticsearch.repository.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Single;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.klartext.data.elasticsearch.repository.api.WordElasticsearchRepository;
import se.klartext.data.elasticsearch.document.Word;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Repository
public class WordElasticsearchRepositoryImpl extends ElasticsearchBaseRepositoryImpl<Word>
        implements WordElasticsearchRepository {

    @Autowired
    public WordElasticsearchRepositoryImpl(TransportClient esClient) {
        super(esClient);
    }

    @Override
    public Single<List<Word>> findWordMatch(String query) {
        QueryBuilder multiMathQuery = multiMatchQuery(query, "value","inflection","translation");

        return this.find(multiMathQuery)
                .map(source -> new ObjectMapper().convertValue(source,Word.class))
                .collect(ArrayList::new,List::add);
    }

    @Override
    public String getDocumentType() {
        return "word";
    }
}
