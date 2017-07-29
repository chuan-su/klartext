package se.klartext.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import se.klartext.app.model.Post;
import se.klartext.app.business.api.PostService;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

/**
 * Created by suchuan on 2017-05-21.
 */
@RestController
@RequestMapping("/api/users/{userId}/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET)
    public Page<Post> getByAuthor(@PathVariable Long userId, Pageable pageable){
        return postService.findByAuthorId(userId,pageable);
    }

    @RequestMapping(method = RequestMethod.POST)
    public DeferredResult<Post> create(@PathVariable Long userId, @RequestBody Post post){
        DeferredResult<Post> result = new DeferredResult<>();
        postService.create(userId,post).subscribe(p -> result.setResult(p));
        return result;
    }

    @RequestMapping(value = "/{postId}",method = RequestMethod.PUT)
    public DeferredResult<Post> update(@PathVariable Long postId,@RequestBody Post post){
        DeferredResult<Post> result = new DeferredResult<>();
        postService.update(postId,post).subscribe(p -> result.setResult(p));
        return result;
    }

    @RequestMapping(value = "/{postId}",method = RequestMethod.DELETE)
    public DeferredResult<Post> delete(@PathVariable Long postId){
        DeferredResult<Post> result = new DeferredResult<>();
        postService.delete(postId).subscribe(p -> result.setResult(p));
        return result;
    }
}
