package se.klartext.app.data.api.elasticsearch;

import io.reactivex.Observable;
import se.klartext.app.model.elasticsearch.ExampleDocument;

public interface ExampleElasticsearchRepository
        extends ElasticsearchBaseRepository<ExampleDocument>{

    Observable<ExampleDocument> findBodyMatch(String... query);
}
