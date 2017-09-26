package se.klartext.app.data.api.elasticsearch;

import io.reactivex.Observable;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.repository.NoRepositoryBean;
import se.klartext.app.model.elasticsearch.BaseDocument;

import java.util.Map;

@NoRepositoryBean
public interface ElasticsearchBaseRepository<D extends BaseDocument> {

    Observable<D> save(D document);

    Observable<D> delete(D document);

    Observable<Map<String, Object>> find(QueryBuilder queryBuilder);
}
