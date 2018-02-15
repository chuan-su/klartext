package se.klartext.api;

import io.reactivex.Observable;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import se.klartext.data.elasticsearch.repository.api.WordElasticsearchRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class WordController {

    @Autowired
    private WordElasticsearchRepository wordRepo;

    @RequestMapping(value = "/api/words/search",method = RequestMethod.GET)
    public DeferredResult search(
            @RequestParam(value = "query",required = true) String query) {

        DeferredResult<List> result = new DeferredResult<>();
        wordRepo.findWordMatch(query)
                .subscribe(
                        result::setResult,
                        result::setErrorResult
                );



        Observable<Long> values = Observable.interval(100, TimeUnit.MILLISECONDS);

        Subscription subscription = (Subscription) values
            .skipWhile(v -> v < 2)
            .map(v -> v * 2)
            .subscribe(
                v -> System.out.println(v),
                e -> System.out.println("Error: " + e),
                () -> System.out.println("Completed")
            );

        // Output:
        // 4
        // 6
        // 8
        // ...
        subscription.c


    }
}
