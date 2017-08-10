package se.klartext.app.data.impl.elasticsearch;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import se.klartext.app.data.api.elasticsearch.ElasticsearchBaseRepository;
import se.klartext.app.model.elasticsearch.BaseDocument;

import java.util.Map;

public class ElasticsearchBaseRepositoryImpl<D extends BaseDocument> implements ElasticsearchBaseRepository<D> {

    @Autowired
    private TransportClient es;

    @Override
    public D save(D document) {
        Map<String,Object> m = document.getSource();
        es.prepareIndex("klartext", document.getDocumentType(),String.valueOf(document.getId()))
                .setSource(document.getSource())
                .get();
        return document;
    }

    @Override
    public void delete(D document) {
        es.prepareDelete("klartext",document.getDocumentType(),String.valueOf(document.getId())).get();
    }
}
