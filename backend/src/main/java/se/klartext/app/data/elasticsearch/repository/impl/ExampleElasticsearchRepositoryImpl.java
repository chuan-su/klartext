package se.klartext.app.data.elasticsearch.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Observable;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import se.klartext.app.data.elasticsearch.repository.api.ExampleElasticsearchRepository;
import se.klartext.app.data.elasticsearch.document.Example;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

@Repository
public class ExampleElasticsearchRepositoryImpl extends ElasticsearchBaseRepositoryImpl<Example>
        implements ExampleElasticsearchRepository {

    private final String DOCUMENT_TYPE = "example";

    @Autowired
    public ExampleElasticsearchRepositoryImpl(TransportClient esClient) {
        super(esClient);
    }

    @Override
    public Observable<Example> findBodyMatch(String... query){

        final Function<List<MatchQueryBuilder>,BoolQueryBuilder> addToBoolQuery = list -> {
            BoolQueryBuilder boolQueryBuilder = boolQuery();
            boolQueryBuilder.should().addAll(list);
            return boolQueryBuilder;
        };

        BoolQueryBuilder boolQuery = Stream.of(query)
                .map(word -> QueryBuilders.matchQuery("body",word))
                .collect(collectingAndThen(toList(),addToBoolQuery));

        return this.find(boolQuery)
                .map(source -> new ObjectMapper().convertValue(source,Example.class));

    }

    @Override
    public String getDocumentType() {
        return DOCUMENT_TYPE;
    }
}
