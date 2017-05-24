package se.klartext.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import se.klartext.app.models.Post;
import se.klartext.app.models.PostRepository;
import se.klartext.app.models.User;
import se.klartext.app.models.UserRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by suchuan on 2017-05-21.
 */
@RestController
@RequestMapping("/users/{userId}/posts")
public class PostController {

    private final UserRepository userRepo;

    private final PostRepository postRepo;

    @Autowired
    public PostController(UserRepository userRepo, PostRepository postRepo){
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Post> getByAuthor(@PathVariable Long userId,Pageable pageable){
        return postRepo.findByCreatedById(userId,pageable).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public Post create(@PathVariable Long userId,@RequestBody Post post){
        User user =  userRepo.findOne(userId);
        Post newPost = Post.builder()
                .body(post.getBody())
                .translation(post.getTranslation())
                .createdBy(post.getCreatedBy())
                .build();

        return postRepo.save(newPost);
    }

    @RequestMapping(value = "/{postId}",method = RequestMethod.PUT)
    public Post update(@PathVariable Long postId,@RequestBody Post post){
        Post toUpdate = findPostById(postId);
        toUpdate.setBody(post.getBody());
        toUpdate.setTranslation(post.getTranslation());
        return postRepo.save(toUpdate);
    }

    private Post findPostById(Long id){
        Optional<Post> post = postRepo.findOne(id);
        return post.orElseThrow(() -> new RuntimeException("not found"));
    }
}
