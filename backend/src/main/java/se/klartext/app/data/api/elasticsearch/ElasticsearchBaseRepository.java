package se.klartext.app.data.api.elasticsearch;

import io.reactivex.Observable;
import org.elasticsearch.index.query.QueryBuilder;
import se.klartext.app.model.elasticsearch.BaseDocument;

import java.util.Map;


public interface ElasticsearchBaseRepository<D extends BaseDocument> {

    Observable<D> save(D document);

    Observable<D> delete(D document);

    Observable<Map<String, Object>> find(QueryBuilder queryBuilder);
}
