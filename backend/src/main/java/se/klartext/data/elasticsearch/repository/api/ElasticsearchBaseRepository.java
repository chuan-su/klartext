package se.klartext.data.elasticsearch.repository.api;

import io.reactivex.Observable;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.repository.NoRepositoryBean;
import se.klartext.data.elasticsearch.document.BaseDocument;

import java.util.Map;

@NoRepositoryBean
public interface ElasticsearchBaseRepository<D extends BaseDocument> {

    Observable<D> save(D document);

    Observable<D> delete(D document);

    Observable<Map<String, Object>> find(QueryBuilder queryBuilder);
}
