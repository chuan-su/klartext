package se.klartext.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import se.klartext.business.api.ExampleService;
import se.klartext.lib.exception.HttpUnauthorizedException;
import se.klartext.domain.model.post.comment.Comment;
import se.klartext.domain.model.user.User;
import se.klartext.data.elasticsearch.document.Example;
import se.klartext.security.impl.UserDetailsImpl;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by suchuan on 2017-05-21.
 */
@RestController
public class ExampleController {

    private final ExampleService<se.klartext.domain.model.post.example.Example,Example> exampleService;

    @Autowired
    public ExampleController(ExampleService exampleService){
        this.exampleService = exampleService;
    }

    @RequestMapping(value = "/api/examples/{postId}/comments",method = RequestMethod.POST)
    public DeferredResult<Comment> createComment(@PathVariable Long postId, @RequestBody Comment comment){
        return null;
    }

    @Transactional
    @RequestMapping(value = "/api/users/{userId}/examples",method = RequestMethod.GET)
    public Page<Example> getByAuthor(@PathVariable Long userId, Pageable pageable){
        return exampleService.findByAuthorId(userId,pageable);
    }

    @RequestMapping(value = "/api/examples",method = RequestMethod.POST)
    public DeferredResult<Example> create(@RequestBody se.klartext.domain.model.post.example.Example example){

        User user = getUserFromSecurityContext();

        DeferredResult<Example> result = new DeferredResult<>();
        exampleService.create(example,user)
                .subscribe(
                        result::setResult,
                        result::setErrorResult
                );
        return result;
    }

    @RequestMapping(value = "/api/examples/{postId}",method = RequestMethod.PUT)
    public DeferredResult<Example> update(@PathVariable Long postId, @RequestBody se.klartext.domain.model.post.example.Example example){
        DeferredResult<Example> result = new DeferredResult<>();
        exampleService.update(postId, example)
                .subscribe(
                        result::setResult,
                        result::setErrorResult
                );
        return result;
    }

    @RequestMapping(value = "/api/examples/{postId}",method = RequestMethod.DELETE)
    public DeferredResult<Example> delete(@PathVariable Long postId){
        DeferredResult<Example> result = new DeferredResult<>();
        exampleService.delete(postId)
                .subscribe(
                        result::setResult,
                        result::setErrorResult
                );
        return result;
    }

    @RequestMapping(value = "/api/examples/{postId}/likes",method = RequestMethod.POST)
    public DeferredResult<Example> addLikes(@PathVariable Long postId){
        User user = getUserFromSecurityContext();
        DeferredResult<Example> result = new DeferredResult<>();
        exampleService.addLikes(postId,user)
                .subscribe(
                        result::setResult,
                        result::setErrorResult
                );
        return result;
    }

    @RequestMapping(value = "/api/examples/{postId}/likes",method = RequestMethod.DELETE)
    public DeferredResult<Example> deleteLikes(@PathVariable Long postId){
        User user = getUserFromSecurityContext();
        DeferredResult<Example> result = new DeferredResult<>();
        exampleService.deleteLikes(postId,user)
                .subscribe(
                        result::setResult,
                        result::setErrorResult
                );
        return result;
    }

    @RequestMapping(value = "/api/examples/search",method = RequestMethod.GET)
    public DeferredResult search(
            @RequestParam(value = "query",required = true) String query) {

        DeferredResult<List> result = new DeferredResult<>();
        exampleService.findBodyMatch(query)
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
