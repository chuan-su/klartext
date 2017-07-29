package se.klartext.app.business.api;


import io.reactivex.Observable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se.klartext.app.model.Post;

import java.util.stream.Stream;

/**
 * Created by suchuan on 2017-06-03.
 */

public interface PostService {

    Page<Post> findByAuthorId(Long userId, Pageable pageable);

    Post findOne(Long id);

    Observable<Post> update(Long postId, Post post);

    Observable<Post> create(Long userId, Post post);

    Observable<Post> delete(Long postId);
}