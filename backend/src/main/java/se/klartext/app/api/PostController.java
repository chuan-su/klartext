package se.klartext.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import se.klartext.app.business.api.PostService;
import se.klartext.app.lib.exception.HttpUnauthorizedException;
import se.klartext.app.model.Post;
import se.klartext.app.model.User;
import se.klartext.app.model.elasticsearch.PostDocument;
import se.klartext.app.security.impl.UserDetailsImpl;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by suchuan on 2017-05-21.
 */
@RestController
public class PostController {

    private final PostService<Post,PostDocument> postService;

    @Autowired
    public PostController(PostService postService){
        this.postService = postService;
    }

    @Transactional
    @RequestMapping(value = "/api/users/{userId}/posts",method = RequestMethod.GET)
    public Page<Post> getByAuthor(@PathVariable Long userId, Pageable pageable){
        return postService.findByAuthorId(userId,pageable);
    }

    @RequestMapping(value = "/api/posts",method = RequestMethod.POST)
    public DeferredResult<PostDocument> create(@RequestBody Post post){

        User user = getUserFromSecurityContext();

        DeferredResult<PostDocument> result = new DeferredResult<>();
        postService.create(post,user)
                .subscribe(
                        result::setResult,
                        result::setErrorResult
                );
        return result;
    }

    @RequestMapping(value = "/api/posts/{postId}",method = RequestMethod.PUT)
    public DeferredResult<PostDocument> update(@PathVariable Long postId,@RequestBody Post post){
        DeferredResult<PostDocument> result = new DeferredResult<>();
        postService.update(postId,post)
                .subscribe(
                        result::setResult,
                        result::setErrorResult
                );
        return result;
    }

    @RequestMapping(value = "/api/posts/{postId}",method = RequestMethod.DELETE)
    public DeferredResult<PostDocument> delete(@PathVariable Long postId){
        DeferredResult<PostDocument> result = new DeferredResult<>();
        postService.delete(postId)
                .subscribe(
                        result::setResult,
                        result::setErrorResult
                );
        return result;
    }

    @RequestMapping(value = "/api/posts/{postId}/likes",method = RequestMethod.POST)
    public DeferredResult<PostDocument> addLikes(@PathVariable Long postId){
        User user = getUserFromSecurityContext();
        DeferredResult<PostDocument> result = new DeferredResult<>();
        postService.addLikes(postId,user)
                .subscribe(
                        result::setResult,
                        result::setErrorResult
                );
        return result;
    }

    @RequestMapping(value = "/api/posts/{postId}/likes",method = RequestMethod.DELETE)
    public DeferredResult<PostDocument> deleteLikes(@PathVariable Long postId){
        User user = getUserFromSecurityContext();
        DeferredResult<PostDocument> result = new DeferredResult<>();
        postService.deleteLikes(postId,user)
                .subscribe(
                        result::setResult,
                        result::setErrorResult
                );
        return result;
    }

    @RequestMapping(value = "/api/posts/search",method = RequestMethod.GET)
    public DeferredResult search(
            @RequestParam(value = "query",required = true) String query) {

        DeferredResult<List> result = new DeferredResult<>();
        postService.findBodyMatch(query)
                .subscribe(
                        result::setResult,
                        result::setErrorResult
                );
        return result;
    }

    private User getUserFromSecurityContext(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return Optional.ofNullable(((UserDetailsImpl)userDetails).getUser())
                .orElseThrow(() -> new HttpUnauthorizedException("authenticated user is required"));
    }
}
