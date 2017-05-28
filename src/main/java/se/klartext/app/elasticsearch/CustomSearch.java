package se.klartext.app.elasticsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.stream.Stream;

/**
 * Created by suchuan on 2017-05-28.
 */
public interface CustomSearch<T> {

    Stream<T> findMatches(String term);
}
