package se.klartext.app.business.api;


import io.reactivex.Observable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se.klartext.app.model.BaseEntity;
import se.klartext.app.model.Post;
import se.klartext.app.model.User;
import se.klartext.app.model.elasticsearch.BaseDocument;
import se.klartext.app.model.elasticsearch.PostDocument;

import java.util.List;

/**
 * Created by suchuan on 2017-06-03.
 */

public interface PostService<E extends BaseEntity, D extends BaseDocument> {

    Page<PostDocument> findByAuthorId(Long userId, Pageable pageable);

    E findOne(Long id);

    Observable<PostDocument> update(Long postId, Post post);

    Observable<PostDocument> create(Post post, User user);

    Observable<D> delete(Long postId);

    Observable<D> addLikes(Long postId, User user);

    Observable<D> deleteLikes(Long postId, User user);

    Observable<List<D>> findBodyMatch(String string);
}
