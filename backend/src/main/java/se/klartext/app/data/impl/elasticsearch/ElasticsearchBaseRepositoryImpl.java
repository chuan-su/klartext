package se.klartext.app.data.impl.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Observable;
import lombok.Getter;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import se.klartext.app.data.api.elasticsearch.ElasticsearchBaseRepository;
import se.klartext.app.lib.exception.ElasticsearchRequestException;
import se.klartext.app.model.elasticsearch.BaseDocument;

import java.util.Map;

import static io.reactivex.Observable.fromCallable;

public abstract class ElasticsearchBaseRepositoryImpl<D extends BaseDocument>
        implements ElasticsearchBaseRepository<D> {

    @Getter
    private final TransportClient esClient;

    private final String INDEX_NAME = "klartext";

    protected ElasticsearchBaseRepositoryImpl(TransportClient esClient) {
        this.esClient = esClient;
    }

    @Override
    public Observable<D> save(D document) {

        final String indexName = this.getIndexName();
        final String docType = this.getDocumentType();
        final String docId = String.valueOf(document.getId());

        return fromCallable(() ->
                esClient.prepareIndex(indexName, docType, docId)
                        .setSource(new ObjectMapper().convertValue(document, Map.class))
                        .get())
                .map(indexResponse -> {
                    if(indexResponse.status() == RestStatus.OK || indexResponse.status() == RestStatus.CREATED){
                        return document;
                    }else{
                        throw new ElasticsearchRequestException("Failed index document " + docId);
                    }
                });
    }

    @Override
    public Observable<D> delete(D document) {

        final String indexName = this.getIndexName();
        final String docType = this.getDocumentType();
        final String docId = String.valueOf(document.getId());

        return fromCallable(() -> esClient.prepareDelete(indexName, docType, docId).get())
                .flatMap(deleteResponse -> {
                    if (deleteResponse.status() != RestStatus.OK) {
                        return Observable.error(new ElasticsearchRequestException("Failed deleted document " + docId));
                    }else{
                        return Observable.empty();
                    }
                });
    }

    @Override
    public Observable<Map<String, Object>> find(QueryBuilder queryBuilder) {

        final String indexName = this.getIndexName();
        final String docType = this.getDocumentType();

        return fromCallable(() -> esClient.prepareSearch(indexName).setTypes(docType)
                .setQuery(queryBuilder).get())
                .flatMap(searchResponse -> Observable.fromArray(searchResponse.getHits().getHits()))
                .map(hit -> {
                    Map<String,Object> source = hit.getSource();
                    source.put("id",hit.getId()); // source.put("score",hit.getScore());
                    return source;
                });
    }

    public final String getIndexName(){
        return INDEX_NAME;
    }

    public abstract String getDocumentType();
}
