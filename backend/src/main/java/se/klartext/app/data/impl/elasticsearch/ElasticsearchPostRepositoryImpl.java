package se.klartext.app.data.impl.elasticsearch;

import org.springframework.stereotype.Repository;
import se.klartext.app.data.api.elasticsearch.ElasticsearchPostRepository;
import se.klartext.app.model.elasticsearch.PostDocument;

import java.util.stream.Stream;

@Repository
public class ElasticsearchPostRepositoryImpl extends ElasticsearchBaseRepositoryImpl<PostDocument>
        implements ElasticsearchPostRepository{

    @Override
    public Stream<PostDocument> findBy(String query) {
        return null;
    }
}
