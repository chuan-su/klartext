package se.klartext.app.data.elasticsearch.converter;

import se.klartext.app.data.jpa.entity.Example;
import se.klartext.app.data.elasticsearch.document.ExampleDocument;

import java.util.stream.Collectors;

public class PostDocumentConverter extends DocumentConverter<Example,ExampleDocument> {

    public PostDocumentConverter() {
        super(post ->
                ExampleDocument.builder()
                        .id(String.valueOf(post.getId()))
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
