package se.klartext.app.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by suchuan on 2017-05-28.
 */
public interface WordRepository
        extends ElasticsearchRepository<Word,String>,CustomSearch<Word>{
}
