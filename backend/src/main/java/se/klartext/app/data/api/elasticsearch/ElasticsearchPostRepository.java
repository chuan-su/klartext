package se.klartext.app.data.api.elasticsearch;

import io.reactivex.Observable;
import se.klartext.app.model.elasticsearch.PostDocument;

import java.util.List;

public interface ElasticsearchPostRepository
        extends ElasticsearchBaseRepository<PostDocument>{

    Observable<List<PostDocument>> findBodyMatch(String... query);
}
