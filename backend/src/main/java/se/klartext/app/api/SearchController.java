package se.klartext.app.api;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

/**
 * Created by suchuan on 2017-05-28.
 */

@RestController
@RequestMapping(value = "/search")
public class SearchController {
    private TransportClient es;

    @Autowired
    public SearchController(TransportClient es){
        this.es = es;
    }

    @RequestMapping(value = "/words")
    public Iterable getWords(@RequestParam(value = "query",required = true) String query){
        SearchResponse response = es.prepareSearch("klartext")
                .setTypes("word")
                .setQuery(multiMatchQuery(
                        query,
                        "value","inflection","translation"
                ))
                .get();

        return Stream.of(response.getHits().getHits())
                .map(SearchHit::getSource)
                .collect(Collectors.toList());
    }
}
