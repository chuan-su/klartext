package se.klartext.app.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.klartext.app.business.api.LikeService;
import se.klartext.app.data.api.LikeRepository;
import se.klartext.app.model.Like;
import se.klartext.app.model.Post;
import se.klartext.app.model.User;

import java.util.Objects;

@Component
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepo;

    @Override
    public Like addIfNotPresent(Post post, User user) {

        Objects.requireNonNull(post);
        Objects.requireNonNull(user);
        Objects.requireNonNull(post.getId());
        Objects.requireNonNull(user.getId());

        return likeRepo.findByUserIdAndPostId(user.getId(),post.getId())
                .orElseGet(()->{
                    Like like = Like.builder().post(post).user(user).build();
                    return likeRepo.save(like);
                });
    }

    @Override
    public void deleteIfPresent(Post post, User user) {

        Objects.requireNonNull(post);
        Objects.requireNonNull(user);
        Objects.requireNonNull(post.getId());
        Objects.requireNonNull(user.getId());

        likeRepo.findByUserIdAndPostId(user.getId(),post.getId())
                .ifPresent(likeRepo::delete);
    }
}
