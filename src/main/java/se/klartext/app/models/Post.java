package se.klartext.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.persistence.*;

/**
 * Created by suchuan on 2017-05-21.
 */
@Entity
@Table(name = "posts")
@EntityListeners(PostListener.class)
@JsonDeserialize(builder = Post.Builder.class)
@NoArgsConstructor
@Getter
public class Post extends BaseEntity {

    @Setter
    private String body;

    @Setter
    private String translation;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    private Post(Builder builder) {
        this.body = builder.body;
        this.translation = builder.translation;
        this.createdBy = builder.createdBy;
    }

    public static Builder getBuilder(){
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String body;
        private String translation;
        private User createdBy;

        private Builder(){}

        public Builder withBody(String body){
            this.body = body;
            return this;
        }

        public Builder withTranslation(String translation){
            this.translation = translation;
            return this;
        }

        public Builder withCreatedBy(User createdBy){
            this.createdBy = createdBy;
            return this;
        }

        public Post build(){
            Post post = new Post(this);
            return post;
        }

    }
}
