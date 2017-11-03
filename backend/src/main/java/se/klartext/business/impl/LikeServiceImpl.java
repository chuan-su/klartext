package se.klartext.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.klartext.business.api.LikeService;
import se.klartext.domain.model.post.Like.LikeRepository;
import se.klartext.domain.model.post.example.Example;
import se.klartext.domain.model.post.Like.Like;
import se.klartext.domain.model.user.User;

import java.util.Objects;

@Component
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepo;

    @Override
    public Like addIfNotPresent(Example example, User user) {

        Objects.requireNonNull(example);
        Objects.requireNonNull(user);
        Objects.requireNonNull(example.getId());
        Objects.requireNonNull(user.getId());

        return likeRepo.findByUserIdAndPostId(user.getId(), example.getId())
                .orElseGet(()->{
                    Like like = Like.builder().post(example).user(user).build();
                    return likeRepo.save(like);
                });
    }

    @Override
    public void deleteIfPresent(Example example, User user) {

        Objects.requireNonNull(example);
        Objects.requireNonNull(user);
        Objects.requireNonNull(example.getId());
        Objects.requireNonNull(user.getId());

        likeRepo.findByUserIdAndPostId(user.getId(), example.getId())
                .ifPresent(likeRepo::delete);
    }
}
