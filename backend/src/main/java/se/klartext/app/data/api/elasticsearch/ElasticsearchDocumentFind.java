package se.klartext.app.data.api.elasticsearch;

import se.klartext.app.model.elasticsearch.BaseDocument;

import java.util.stream.Stream;

public interface ElasticsearchDocumentFind<D extends BaseDocument> {

    Stream<D> findBy(String query);
}
