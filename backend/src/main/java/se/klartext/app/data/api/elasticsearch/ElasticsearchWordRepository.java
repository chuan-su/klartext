package se.klartext.app.data.api.elasticsearch;

import io.reactivex.Observable;
import se.klartext.app.model.elasticsearch.WordDocument;

import java.util.List;

public interface ElasticsearchWordRepository extends ElasticsearchBaseRepository<WordDocument> {

    Observable<List<WordDocument>> findWordMatch(String query);
}
