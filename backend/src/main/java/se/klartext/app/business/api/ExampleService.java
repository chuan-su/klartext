package se.klartext.app.business.api;


import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se.klartext.app.data.jpa.entity.BaseEntity;
import se.klartext.app.data.jpa.entity.Example;
import se.klartext.app.data.jpa.entity.User;
import se.klartext.app.data.elasticsearch.document.BaseDocument;
import se.klartext.app.data.elasticsearch.document.ExampleDocument;

import java.util.List;

/**
 * Created by suchuan on 2017-06-03.
 */

public interface ExampleService<E extends BaseEntity, D extends BaseDocument> {

    Page<ExampleDocument> findByAuthorId(Long userId, Pageable pageable);

    Observable<Example> findOne(Long id);

    Observable<ExampleDocument> update(Long postId, Example example);

    Observable<ExampleDocument> create(Example example, User user);

    Observable<D> delete(Long postId);

    Observable<D> addLikes(Long postId, User user);

    Observable<D> deleteLikes(Long postId, User user);

    Single<List<ExampleDocument>> findBodyMatch(String string);
}
