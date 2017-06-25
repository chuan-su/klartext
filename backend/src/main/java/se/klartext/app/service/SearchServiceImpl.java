package se.klartext.app.service;

import io.reactivex.Observable;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.elasticsearch.index.query.QueryBuilders.*;


/**
 * Created by chuan on 2017-06-25.
 */
@Service
public class SearchServiceImpl implements SearchService<SearchResponse> {

    @Autowired
    private TransportClient es;

    @Override
    public Observable<SearchResponse> findMatch(String type, String query){
        switch (type){
            case "post":
                return findMatchPosts(query);
            case "word":
                return findMatchWords(query);
            default:
                throw new RuntimeException("Not supported search type");
        }
    }

    private Observable<SearchResponse> findMatchWords(String query) {
        return Observable.just(query)
                .map(q -> {
                    SearchResponse response = es.prepareSearch("klartext")
                            .setTypes("word")
                            .setQuery(multiMatchQuery(
                                    query,
                                    "value","inflection","translation"
                            ))
                            .get();
                    return response;
                });
    }
    private Observable<SearchResponse> findMatchPosts(String query) {
        // build bool query for multiple field match
        BoolQueryBuilder boolQuery = Pattern.compile(",").splitAsStream(query)
                .map(word -> QueryBuilders.matchQuery("body",word))
                .collect(
                        collectingAndThen(toList(), (list) -> {
                            BoolQueryBuilder boolQueryBuilder = boolQuery();
                            boolQueryBuilder.should().addAll(list);
                            return boolQueryBuilder;
                }));
        return Observable.just(boolQuery)
                .map(bq -> {
                    SearchResponse response = es.prepareSearch("klartext")
                            .setTypes("post")
                            .setQuery(bq)
                            .get();
                    return response;
                });
    }
}
