package se.klartext.app.api;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import se.klartext.app.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by suchuan on 2017-05-28.
 */

@RestController
public class SearchController {

    private final SearchService<SearchResponse> searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(value = "/search/{docType}")
    public DeferredResult index(
            @PathVariable String docType,
            @RequestParam(value = "query",required = true) String query) {

        DeferredResult<Iterable> result = new DeferredResult<>();
        this.searchService.findMatch(docType,query)
                .subscribe(searchResult ->{
                    List<?> data =Stream.of(searchResult.getHits().getHits())
                            .map(SearchHit::getSource)
                            .collect(Collectors.toList());
                    result.setResult(data);
        });
        return result;
    }
}
