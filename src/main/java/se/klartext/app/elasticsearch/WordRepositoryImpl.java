package se.klartext.app.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.stream.Stream;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

/**
 * Created by suchuan on 2017-05-28.
 */
public class WordRepositoryImpl implements CustomSearch<Word> {

    @Autowired
    private ElasticsearchTemplate es;

    @Override
    public Stream<Word> findMatches(String term) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(
                        term,
                        "value","inflection","translation"
                ))
                .build();
        return es.queryForList(searchQuery,Word.class).stream();
    }
}
