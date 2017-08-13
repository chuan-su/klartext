package se.klartext.app.business.impl;

import io.reactivex.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.klartext.app.business.api.PostService;
import se.klartext.app.data.api.LikeRepository;
import se.klartext.app.data.api.PostRepository;
import se.klartext.app.data.api.elasticsearch.ElasticsearchPostRepository;
import se.klartext.app.model.*;
import se.klartext.app.lib.converter.PostDocumentConverter;
import se.klartext.app.model.elasticsearch.PostDocument;

import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by chuan on 2017-06-18.
 */
@Service
public class PostServiceImpl implements PostService<Post,PostDocument> {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private LikeRepository likeRepo;

    @Autowired
    private ElasticsearchPostRepository esPostRepo;

    @Override
    public Page<Post> findByAuthorId(Long authorId, Pageable pageable) {
        return postRepo.findByCreatedById(authorId,pageable);
    }

    @Override
    public Post findOne(Long id) {
        return postRepo.findOne(id).orElseThrow(
                () -> new RuntimeException("not found"));
    }

    @Override
    public Observable<PostDocument> update(Long id, Post post) {
        return Observable.just(findOne(id))
                .map(p -> {
                    p.setBody(post.getBody());
                    p.setInterp(post.getInterp());
                    return postRepo.save(p);
                })
                .map(p -> new PostDocumentConverter().convertFromEntity(p))
                .flatMap(esPostRepo::save);
    }

    @Override
    public Observable<PostDocument> create(Post postData, User user){
        postData.setCreatedBy(user);
        return Observable.just(postRepo.save(postData))
                .map(post -> new PostDocumentConverter().convertFromEntity(post))
                .flatMap(postDocument -> esPostRepo.save(postDocument));
    }

    @Override
    public Observable<PostDocument> delete(Long postId) {
        return Observable.just(findOne(postId))
                .map(post -> {
                    postRepo.delete(post);
                    return post;
                })
                .map(post -> new PostDocumentConverter().convertFromEntity(post))
                .doOnNext(postDoc -> esPostRepo.delete(postDoc));
    }

    @Override
    public Observable<PostDocument> addLikes(Long postId, User user){
        return Observable.just(findOne(postId))
                .map(post -> {
                    Like like = likeRepo.findByUserIdAndPostId(user.getId(),post.getId())
                            .orElse(Like.builder().post(post).user(user).build());
                    likeRepo.save(like);
                    return post;
                })
                .map(post -> new PostDocumentConverter().convertFromEntity(post))
                .flatMap(postDocument -> esPostRepo.save(postDocument));
    }

    @Override
    public Observable<PostDocument> deleteLikes(Long postId, User user){
        return Observable.just(findOne(postId))
                .map(post -> {
                    likeRepo.findByUserIdAndPostId(user.getId(),post.getId())
                            .ifPresent(like -> { likeRepo.delete(like);});
                    return post;
                })
                .map(post -> new PostDocumentConverter().convertFromEntity(post))
                .flatMap(postDocument -> esPostRepo.save(postDocument));
    }

    @Override
    public Observable<List<PostDocument>> findBodyMatch(String query) {
        String[] terms = query.split(",");
        return esPostRepo.findBodyMatch(terms);
    }
}
