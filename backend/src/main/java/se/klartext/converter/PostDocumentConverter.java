package se.klartext.converter;

import se.klartext.domain.model.post.example.Example;

import java.util.stream.Collectors;

public class PostDocumentConverter extends DocumentConverter<Example, se.klartext.data.elasticsearch.document.Example> {

    public PostDocumentConverter() {
        super(post ->
                se.klartext.data.elasticsearch.document.Example.builder()
                        .id(String.valueOf(post.getId()))
                        .body(post.getBody())
                        .interp(post.getTranslation())
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
