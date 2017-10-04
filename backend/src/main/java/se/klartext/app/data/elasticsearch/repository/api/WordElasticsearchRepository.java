package se.klartext.app.data.elasticsearch.repository.api;

import io.reactivex.Single;
import se.klartext.app.data.elasticsearch.document.WordDocument;

import java.util.List;

public interface WordElasticsearchRepository extends ElasticsearchBaseRepository<WordDocument> {

    Single<List<WordDocument>> findWordMatch(String query);
}
