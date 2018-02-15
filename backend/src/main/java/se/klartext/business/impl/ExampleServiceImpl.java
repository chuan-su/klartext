package se.klartext.business.impl;

import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.klartext.business.api.LikeService;
import se.klartext.business.api.ExampleService;
import se.klartext.domain.model.post.comment.CommentRepository;
import se.klartext.domain.model.post.example.ExampleRepository;
import se.klartext.data.elasticsearch.repository.api.ExampleElasticsearchRepository;
import se.klartext.domain.model.post.comment.Comment;
import se.klartext.domain.model.user.User;
import se.klartext.converter.PostDocumentConverter;
import se.klartext.data.elasticsearch.document.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.reactivex.Observable.fromCallable;

/**
 * Created by chuan on 2017-06-18.
 */
@Service
public class ExampleServiceImpl implements ExampleService<se.klartext.domain.model.post.example.Example,Example> {

    @Autowired
    private ExampleRepository postRepo;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ExampleElasticsearchRepository esPostRepo;

    @Autowired
    private PostTreePathRepository treeNodePathRepo;

    @Autowired
    private CommentRepository commentRepo;

    public Single<List<Comment>> getComment(Long id){
        return null;
    }

    @Override
    public Page<Example> findByAuthorId(Long authorId, Pageable pageable) {
        return postRepo.findByCreatedById(authorId,pageable)
                .map(post -> new PostDocumentConverter().convertFromEntity(post));
    }

    @Override
    public Observable<se.klartext.domain.model.post.example.Example> findOne(Long id) {
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
    public Observable<Example> update(Long id, se.klartext.domain.model.post.example.Example example) {
        return findOne(id)
                .flatMap(p -> {
                    p.setBody(example.getBody());
                    p.setTranslation(example.getTranslation());
                    return fromCallable(() ->  postRepo.save(p));
                })
                .map(p -> new PostDocumentConverter().convertFromEntity(p))
                .flatMap(esPostRepo::save);
    }

    @Override
    public Observable<Example> create(se.klartext.domain.model.post.example.Example exampleData, User user){

        return fromCallable(() -> {
            exampleData.setCreatedBy(user);
            return postRepo.save(exampleData);
        }).map(post -> new PostDocumentConverter().convertFromEntity(post))
                .flatMap(postDocument -> esPostRepo.save(postDocument));
    }

    @Override
    public Observable<Example> delete(Long postId) {
        return findOne(postId)
                .doOnNext(post -> postRepo.delete(post))
                .map(post -> new PostDocumentConverter().convertFromEntity(post))
                .flatMap(esPostRepo::delete);
    }

    @Override
    public Observable<Example> addLikes(Long postId, User user){
        Objects.requireNonNull(user);
        Objects.requireNonNull(user.getId());

        return findOne(postId)
                .doOnNext(post -> likeService.addIfNotPresent(post,user))
                .map(post -> new PostDocumentConverter().convertFromEntity(post))
                .flatMap(postDocument -> esPostRepo.save(postDocument));
    }

    @Override
    public Observable<Example> deleteLikes(Long postId, User user){
        return findOne(postId)
                .doOnNext(post -> likeService.deleteIfPresent(post,user))
                .map(post -> new PostDocumentConverter().convertFromEntity(post))
                .flatMap(postDocument -> esPostRepo.save(postDocument));
    }

    @Override
    public Single<List<Example>> findBodyMatch(String query) {
        String[] terms = query.split(",");
        return esPostRepo.findBodyMatch(terms)
                .collect(ArrayList::new,List::add);
    }
}
