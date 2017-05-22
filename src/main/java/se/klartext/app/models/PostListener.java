package se.klartext.app.models;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

/**
 * Created by suchuan on 2017-05-22.
 */
public class PostListener {

    @PostPersist
    public void indexES(Post post){
        System.out.println(post);
    }

    @PostUpdate
    public void reIndexES(Post post){
        System.out.println(post);
    }

}
