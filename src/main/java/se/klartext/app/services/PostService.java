package se.klartext.app.services;

import io.reactivex.Observable;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.klartext.app.models.Post;
import se.klartext.app.models.PostRepository;
import se.klartext.app.models.UserRepository;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;


/**
 * Created by suchuan on 2017-06-03.
 */
@Service
public class PostService {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TransportClient es;

    public Observable<Post> savePost(long userId, Post post){
        return Observable.just(userRepo.findOne(userId))
                .map(user -> {
                    Post p = Post.builder()
                            .body(post.getBody())
                            .translation(post.getTranslation())
                            .createdBy(user)
                            .build();
                    return postRepo.save(p);
                })
                .map(p -> {
                    IndexResponse res =  es.prepareIndex("klartext","post",String.valueOf(p.getId()))
                            .setSource(jsonBuilder()
                                    .startObject()
                                    .field("body",p.getBody())
                                    .field("translation", p.getTranslation())
                            )
                            .get();
                    return p;
                });
    }

}
