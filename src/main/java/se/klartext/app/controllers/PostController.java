package se.klartext.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.klartext.app.models.Post;
import se.klartext.app.models.PostRepository;
import se.klartext.app.models.User;
import se.klartext.app.models.UserRepository;

import java.util.Collection;

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

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Post> getByAuthor(@PathVariable Long userId){
        return postRepo.findByCreatedById(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Post create(@PathVariable Long userId,@RequestBody Post post){
        User user =  userRepo.findOne(userId);
        return postRepo.save(new Post(post.getBody(),post.getTranslation(),user));
    }
}
