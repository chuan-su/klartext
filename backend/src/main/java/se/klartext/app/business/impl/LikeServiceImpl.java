package se.klartext.app.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.klartext.app.business.api.LikeService;
import se.klartext.app.data.api.jpa.LikeRepository;
import se.klartext.app.model.Example;
import se.klartext.app.model.Like;
import se.klartext.app.model.User;

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

        return likeRepo.findByUserIdAndExampleId(user.getId(), example.getId())
                .orElseGet(()->{
                    Like like = Like.builder().example(example).user(user).build();
                    return likeRepo.save(like);
                });
    }

    @Override
    public void deleteIfPresent(Example example, User user) {

        Objects.requireNonNull(example);
        Objects.requireNonNull(user);
        Objects.requireNonNull(example.getId());
        Objects.requireNonNull(user.getId());

        likeRepo.findByUserIdAndExampleId(user.getId(), example.getId())
                .ifPresent(likeRepo::delete);
    }
}
