package se.klartext.business.api;


import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se.klartext.domain.shared.BaseEntity;
import se.klartext.domain.model.user.User;
import se.klartext.data.elasticsearch.document.BaseDocument;
import se.klartext.domain.model.post.example.Example;

import java.util.List;

/**
 * Created by suchuan on 2017-06-03.
 */

public interface ExampleService<E extends BaseEntity, D extends BaseDocument> {

    Page<se.klartext.data.elasticsearch.document.Example> findByAuthorId(Long userId, Pageable pageable);

    Observable<Example> findOne(Long id);

    Observable<se.klartext.data.elasticsearch.document.Example> update(Long postId, Example example);

    Observable<se.klartext.data.elasticsearch.document.Example> create(Example example, User user);

    Observable<D> delete(Long postId);

    Observable<D> addLikes(Long postId, User user);

    Observable<D> deleteLikes(Long postId, User user);

    Single<List<se.klartext.data.elasticsearch.document.Example>> findBodyMatch(String string);
}
