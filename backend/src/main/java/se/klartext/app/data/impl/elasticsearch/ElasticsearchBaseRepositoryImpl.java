package se.klartext.app.data.impl.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Observable;
import lombok.Getter;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import se.klartext.app.data.api.elasticsearch.ElasticsearchBaseRepository;
import se.klartext.app.lib.exception.ElasticsearchRequestException;
import se.klartext.app.model.elasticsearch.BaseDocument;

import java.util.Map;

public abstract class ElasticsearchBaseRepositoryImpl<D extends BaseDocument>
        implements ElasticsearchBaseRepository<D> {

    @Autowired
    @Getter
    private TransportClient esClient;

    private final String INDEX_NAME = "klartext";

    @Override
    public Observable<D> save(D document) {

        final String indexName = this.getIndexName();
        final String docType = this.getDocumentType();
        final String docId = String.valueOf(document.getId());

        return Observable.just(
                esClient.prepareIndex(indexName,docType,docId)
                        .setSource(new ObjectMapper().convertValue(document,Map.class))
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
    public void delete(D document) {

        final String indexName = this.getIndexName();
        final String docType = this.getDocumentType();
        final String docId = String.valueOf(document.getId());

        DeleteResponse deleteResponse = esClient.prepareDelete(indexName,docType,docId).get();

        if(deleteResponse.status() != RestStatus.OK){
            throw new ElasticsearchRequestException("Failed deleted document " + docId);
        }
    }

    @Override
    public Observable<Map<String, Object>> find(QueryBuilder queryBuilder) {

        final String indexName = this.getIndexName();
        final String docType = this.getDocumentType();

        return Observable.just(
                this.getEsClient().prepareSearch(indexName).setTypes(docType)
                        .setQuery(queryBuilder)
                        .get())
                .map(SearchResponse::getHits)
                .map(SearchHits::getHits)
                .concatMap(Observable::fromArray)
                .map(hit -> {
                    Map<String,Object> source = hit.getSource();
                    source.putIfAbsent("id",hit.getId());
                    return hit.getSource();
                });
    }

    public String getIndexName(){
        return INDEX_NAME;
    }

    public abstract String getDocumentType();
}
