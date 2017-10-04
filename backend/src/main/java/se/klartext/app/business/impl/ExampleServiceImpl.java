package se.klartext.app.business.impl;

import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.klartext.app.business.api.LikeService;
import se.klartext.app.business.api.ExampleService;
import se.klartext.app.data.jpa.repository.api.CommentRepository;
import se.klartext.app.data.jpa.repository.api.ExampleRepository;
import se.klartext.app.data.jpa.repository.api.TreePathRepository;
import se.klartext.app.data.elasticsearch.repository.api.ExampleElasticsearchRepository;
import se.klartext.app.data.jpa.entity.Comment;
import se.klartext.app.data.jpa.entity.Example;
import se.klartext.app.data.jpa.entity.User;
import se.klartext.app.data.elasticsearch.converter.PostDocumentConverter;
import se.klartext.app.data.elasticsearch.document.ExampleDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.reactivex.Observable.fromCallable;

/**
 * Created by chuan on 2017-06-18.
 */
@Service
public class ExampleServiceImpl implements ExampleService<Example,ExampleDocument> {

    @Autowired
    private ExampleRepository postRepo;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ExampleElasticsearchRepository esPostRepo;

    @Autowired
    private TreePathRepository treeNodePathRepo;

    @Autowired
    private CommentRepository commentRepo;

    public Single<List<Comment>> getComment(Long id){
        return null;
    }

    @Override
    public Page<ExampleDocument> findByAuthorId(Long authorId, Pageable pageable) {
        return postRepo.findByCreatedById(authorId,pageable)
                .map(post -> new PostDocumentConverter().convertFromEntity(post));
    }

    @Override
    public Observable<Example> findOne(Long id) {
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
    public Observable<ExampleDocument> update(Long id, Example example) {
        return findOne(id)
                .flatMap(p -> {
                    p.setBody(example.getBody());
                    p.setInterp(example.getInterp());
                    return fromCallable(() ->  postRepo.save(p));
                })
                .map(p -> new PostDocumentConverter().convertFromEntity(p))
                .flatMap(esPostRepo::save);
    }

    @Override
    public Observable<ExampleDocument> create(Example exampleData, User user){

        return fromCallable(() -> {
            exampleData.setCreatedBy(user);
            return postRepo.save(exampleData);
        }).map(post -> new PostDocumentConverter().convertFromEntity(post))
                .flatMap(postDocument -> esPostRepo.save(postDocument));
    }

    @Override
    public Observable<ExampleDocument> delete(Long postId) {
        return findOne(postId)
                .doOnNext(post -> postRepo.delete(post))
                .map(post -> new PostDocumentConverter().convertFromEntity(post))
                .flatMap(esPostRepo::delete);
    }

    @Override
    public Observable<ExampleDocument> addLikes(Long postId, User user){
        Objects.requireNonNull(user);
        Objects.requireNonNull(user.getId());

        return findOne(postId)
                .doOnNext(post -> likeService.addIfNotPresent(post,user))
                .map(post -> new PostDocumentConverter().convertFromEntity(post))
                .flatMap(postDocument -> esPostRepo.save(postDocument));
    }

    @Override
    public Observable<ExampleDocument> deleteLikes(Long postId, User user){
        return findOne(postId)
                .doOnNext(post -> likeService.deleteIfPresent(post,user))
                .map(post -> new PostDocumentConverter().convertFromEntity(post))
                .flatMap(postDocument -> esPostRepo.save(postDocument));
    }

    @Override
    public Single<List<ExampleDocument>> findBodyMatch(String query) {
        String[] terms = query.split(",");
        return esPostRepo.findBodyMatch(terms)
                .collect(ArrayList::new,List::add);
    }
}
