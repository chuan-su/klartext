package se.klartext.app.data.api.elasticsearch;

import org.springframework.stereotype.Repository;
import se.klartext.app.model.elasticsearch.PostDocument;

public interface ElasticsearchPostRepository
        extends ElasticsearchBaseRepository<PostDocument>,ElasticsearchDocumentFind<PostDocument> {

}
