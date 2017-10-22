package se.klartext.app.data.elasticsearch.repository.api;

import io.reactivex.Single;
import se.klartext.app.data.elasticsearch.document.Word;

import java.util.List;

public interface WordElasticsearchRepository extends ElasticsearchBaseRepository<Word> {

    Single<List<Word>> findWordMatch(String query);
}
