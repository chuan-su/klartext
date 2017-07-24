package se.klartext.app.business.impl;

import io.reactivex.Observable;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.klartext.app.business.api.PostService;
import se.klartext.app.model.Post;
import se.klartext.app.data.api.PostRepository;
import se.klartext.app.data.api.UserRepository;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Stream;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by chuan on 2017-06-18.
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TransportClient es;

    @Override
    public Stream<Post> findByAuthorId(Long authorId,Pageable pageable) {
        return postRepo.findByCreatedById(authorId,pageable);
    }

    @Override
    public Post findOne(Long id) {
        Optional<Post> post = postRepo.findOne(id);
        return post.orElseThrow(
                () -> new RuntimeException("not found"));
    }

    @Override
    public Observable<Post> update(Long id, Post post) {
        return Observable.just(findOne(id))
                .map(p -> {
                    p.setBody(post.getBody());
                    p.setInterp(post.getInterp());
                    return postRepo.save(p);
                })
                .map(p -> {
                   UpdateResponse res = es.prepareUpdate("klartext","post",String.valueOf(p.getId()))
                           .setDoc(jsonBuilder()
                                   .startObject()
                                   .field("body",p.getBody())
                                   .field("interp",p.getInterp())
                                   .field("updated_at", p.getUpdatedAt().truncatedTo(ChronoUnit.SECONDS))
                                   .endObject()
                           )
                           .get();
                   return p;
                });
    }

    @Override
    public Observable<Post> create(long userId, Post post){
        return Observable.just(userRepo.findOne(userId))
                .map(user -> {
                    Post p = Post.builder()
                            .body(post.getBody())
                            .interp(post.getInterp())
                            .createdBy(user)
                            .build();
                    return postRepo.save(p);
                })
                .map(p -> {
                    IndexResponse res =  es.prepareIndex("klartext","post",String.valueOf(p.getId()))
                            .setSource(jsonBuilder()
                                    .startObject()
                                    .field("body",p.getBody())
                                    .field("interp", p.getInterp())
                                    .field("created_at",p.getCreatedAt().truncatedTo(ChronoUnit.SECONDS))
                                    .field("updated_at",p.getUpdatedAt().truncatedTo(ChronoUnit.SECONDS))
                                    .endObject()
                            )
                            .get();
                    return p;
                });
    }
}
