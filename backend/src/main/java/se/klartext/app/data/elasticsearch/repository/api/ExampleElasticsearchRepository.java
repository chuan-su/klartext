package se.klartext.app.data.elasticsearch.repository.api;

import io.reactivex.Observable;
import se.klartext.app.data.elasticsearch.document.ExampleDocument;

public interface ExampleElasticsearchRepository
        extends ElasticsearchBaseRepository<ExampleDocument>{

    Observable<ExampleDocument> findBodyMatch(String... query);
}
