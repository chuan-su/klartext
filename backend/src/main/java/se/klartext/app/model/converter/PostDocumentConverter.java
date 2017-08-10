package se.klartext.app.model.converter;

import se.klartext.app.model.Post;
import se.klartext.app.model.elasticsearch.PostDocument;

import java.util.stream.Collectors;

public class PostDocumentConverter extends DocumentConverter<Post,PostDocument> {

    public PostDocumentConverter() {
        super(post ->
                PostDocument.builder()
                        .id(post.getId().intValue())
                        .body(post.getBody())
                        .interp(post.getInterp())
                        .createdBy(new UserDocumentConverter().convertFromEntity(post.getCreatedBy()))
                        .likes(post.getLikes().stream()
                                .map(like -> new LikeDocumentConverter().convertFromEntity(like))
                                .collect(Collectors.toList())
                        )
                        .createdAt(post.getCreatedAt())
                        .updatedAt(post.getUpdatedAt())
                        .build()
        );
    }
}
