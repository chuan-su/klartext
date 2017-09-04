package se.klartext.app.data.impl.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Observable;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.stereotype.Repository;
import se.klartext.app.data.api.elasticsearch.ElasticsearchPostRepository;
import se.klartext.app.model.elasticsearch.PostDocument;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

@Repository
public class ElasticsearchPostRepositoryImpl extends ElasticsearchBaseRepositoryImpl<PostDocument>
        implements ElasticsearchPostRepository{

    private final String DOCUMENT_TYPE = "post";

    @Override
    public Observable<PostDocument> findBodyMatch(String... query){

        final Function<List<MatchQueryBuilder>,BoolQueryBuilder> addToBoolQuery = list -> {
            BoolQueryBuilder boolQueryBuilder = boolQuery();
            boolQueryBuilder.should().addAll(list);
            return boolQueryBuilder;
        };

        BoolQueryBuilder boolQuery = Stream.of(query)
                .map(word -> QueryBuilders.matchQuery("body",word))
                .collect(collectingAndThen(toList(),addToBoolQuery));

        return this.find(boolQuery)
                .map(source -> new ObjectMapper().convertValue(source,PostDocument.class));

    }

    @Override
    public String getDocumentType() {
        return DOCUMENT_TYPE;
    }
}
