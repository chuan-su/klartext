package se.klartext.app.business.impl;

import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.klartext.app.business.api.LikeService;
import se.klartext.app.business.api.PostService;
import se.klartext.app.data.api.jpa.CommentRepository;
import se.klartext.app.data.api.jpa.PostRepository;
import se.klartext.app.data.api.jpa.TreePathRepository;
import se.klartext.app.data.api.elasticsearch.ElasticsearchPostRepository;
import se.klartext.app.lib.converter.PostDocumentConverter;
import se.klartext.app.model.*;
import se.klartext.app.model.elasticsearch.PostDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.reactivex.Observable.fromCallable;

/**
 * Created by chuan on 2017-06-18.
 */
@Service
public class PostServiceImpl implements PostService<Post,PostDocument> {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ElasticsearchPostRepository esPostRepo;

    @Autowired
    private TreePathRepository treeNodePathRepo;

    @Autowired
    private CommentRepository commentRepo;

    public Single<List<Comment>> getComment(Long id){
        return null;
    }

    @Override
    public Page<PostDocument> findByAuthorId(Long authorId, Pageable pageable) {
        return postRepo.findByCreatedById(authorId,pageable)
                .map(post -> new PostDocumentConverter().convertFromEntity(post));
    }

    @Override
    public Observable<Post> findOne(Long id) {
        return Single.fromCallable(() -> postRepo.findOne(id))
                .flatMap(post ->{
                    if(post.isPresent())
                        return Single.just(post.get());
                    else
                        return Single.error(new RuntimeException("not found"));
                })
                .toObservable();
    }

    @Override
    public Observable<PostDocument> update(Long id, Post post) {
        return findOne(id)
                .flatMap(p -> {
                    p.setBody(post.getBody());
                    p.setInterp(post.getInterp());
                    return fromCallable(() ->  postRepo.save(p));
                })
                .map(p -> new PostDocumentConverter().convertFromEntity(p))
                .flatMap(esPostRepo::save);
    }

    @Override
    public Observable<PostDocument> create(Post postData, User user){

        return fromCallable(() -> {
            postData.setCreatedBy(user);
            return postRepo.save(postData);
        }).map(post -> new PostDocumentConverter().convertFromEntity(post))
                .flatMap(postDocument -> esPostRepo.save(postDocument));
    }

    @Override
    public Observable<PostDocument> delete(Long postId) {
        return findOne(postId)
                .doOnNext(post -> postRepo.delete(post))
                .map(post -> new PostDocumentConverter().convertFromEntity(post))
                .flatMap(esPostRepo::delete);
    }

    @Override
    public Observable<PostDocument> addLikes(Long postId, User user){
        Objects.requireNonNull(user);
        Objects.requireNonNull(user.getId());

        return findOne(postId)
                .doOnNext(post -> likeService.addIfNotPresent(post,user))
                .map(post -> new PostDocumentConverter().convertFromEntity(post))
                .flatMap(postDocument -> esPostRepo.save(postDocument));
    }

    @Override
    public Observable<PostDocument> deleteLikes(Long postId, User user){
        return findOne(postId)
                .doOnNext(post -> likeService.deleteIfPresent(post,user))
                .map(post -> new PostDocumentConverter().convertFromEntity(post))
                .flatMap(postDocument -> esPostRepo.save(postDocument));
    }

    @Override
    public Single<List<PostDocument>> findBodyMatch(String query) {
        String[] terms = query.split(",");
        return esPostRepo.findBodyMatch(terms)
                .collect(ArrayList::new,List::add);
    }
}
