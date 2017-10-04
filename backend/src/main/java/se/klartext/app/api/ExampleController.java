package se.klartext.app.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import se.klartext.app.business.api.ExampleService;
import se.klartext.app.lib.exception.HttpUnauthorizedException;
import se.klartext.app.data.jpa.entity.Comment;
import se.klartext.app.data.jpa.entity.Example;
import se.klartext.app.data.jpa.entity.User;
import se.klartext.app.data.elasticsearch.document.ExampleDocument;
import se.klartext.app.security.impl.UserDetailsImpl;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Created by suchuan on 2017-05-21.
 */
@RestController
public class ExampleController {

    private final ExampleService<Example,ExampleDocument> exampleService;

    @Autowired
    public ExampleController(ExampleService exampleService){
        this.exampleService = exampleService;
    }

    @RequestMapping(value = "/api/examples/{postId}/comments",method = RequestMethod.POST)
    public DeferredResult<Comment> createComment(@PathVariable Long postId,@RequestBody Comment comment){
        return null;
    }

    @Transactional
    @RequestMapping(value = "/api/users/{userId}/examples",method = RequestMethod.GET)
    public Page<ExampleDocument> getByAuthor(@PathVariable Long userId, Pageable pageable){
        return exampleService.findByAuthorId(userId,pageable);
    }

    @RequestMapping(value = "/api/examples",method = RequestMethod.POST)
    public DeferredResult<ExampleDocument> create(@RequestBody Example example){

        User user = getUserFromSecurityContext();

        DeferredResult<ExampleDocument> result = new DeferredResult<>();
        exampleService.create(example,user)
                .subscribe(
                        result::setResult,
                        result::setErrorResult
                );
        return result;
    }

    @RequestMapping(value = "/api/examples/{postId}",method = RequestMethod.PUT)
    public DeferredResult<ExampleDocument> update(@PathVariable Long postId, @RequestBody Example example){
        DeferredResult<ExampleDocument> result = new DeferredResult<>();
        exampleService.update(postId, example)
                .subscribe(
                        result::setResult,
                        result::setErrorResult
                );
        return result;
    }

    @RequestMapping(value = "/api/examples/{postId}",method = RequestMethod.DELETE)
    public DeferredResult<ExampleDocument> delete(@PathVariable Long postId){
        DeferredResult<ExampleDocument> result = new DeferredResult<>();
        exampleService.delete(postId)
                .subscribe(
                        result::setResult,
                        result::setErrorResult
                );
        return result;
    }

    @RequestMapping(value = "/api/examples/{postId}/likes",method = RequestMethod.POST)
    public DeferredResult<ExampleDocument> addLikes(@PathVariable Long postId){
        User user = getUserFromSecurityContext();
        DeferredResult<ExampleDocument> result = new DeferredResult<>();
        exampleService.addLikes(postId,user)
                .subscribe(
                        result::setResult,
                        result::setErrorResult
                );
        return result;
    }

    @RequestMapping(value = "/api/examples/{postId}/likes",method = RequestMethod.DELETE)
    public DeferredResult<ExampleDocument> deleteLikes(@PathVariable Long postId){
        User user = getUserFromSecurityContext();
        DeferredResult<ExampleDocument> result = new DeferredResult<>();
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
