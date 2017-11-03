package se.klartext.data.elasticsearch.repository.api;

import io.reactivex.Observable;
import se.klartext.data.elasticsearch.document.Example;

public interface ExampleElasticsearchRepository
        extends ElasticsearchBaseRepository<Example>{

    Observable<Example> findBodyMatch(String... query);
}
