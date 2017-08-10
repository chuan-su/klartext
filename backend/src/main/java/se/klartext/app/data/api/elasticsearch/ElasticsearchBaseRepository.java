package se.klartext.app.data.api.elasticsearch;

import org.springframework.stereotype.Repository;
import se.klartext.app.model.elasticsearch.BaseDocument;


public interface ElasticsearchBaseRepository<D extends BaseDocument> {

    D save(D document);

    void delete(D document);
}
