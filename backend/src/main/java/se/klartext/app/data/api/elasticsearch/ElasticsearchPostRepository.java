package se.klartext.app.data.api.elasticsearch;

import io.reactivex.Observable;
import se.klartext.app.model.elasticsearch.PostDocument;

public interface ElasticsearchPostRepository
        extends ElasticsearchBaseRepository<PostDocument>{

    Observable<PostDocument> findBodyMatch(String... query);
}
