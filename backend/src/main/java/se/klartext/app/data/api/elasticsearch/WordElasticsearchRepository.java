package se.klartext.app.data.api.elasticsearch;

import io.reactivex.Single;
import se.klartext.app.model.elasticsearch.WordDocument;

import java.util.List;

public interface WordElasticsearchRepository extends ElasticsearchBaseRepository<WordDocument> {

    Single<List<WordDocument>> findWordMatch(String query);
}
