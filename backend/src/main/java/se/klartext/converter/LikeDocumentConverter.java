package se.klartext.converter;

import se.klartext.domain.model.post.Like.Like;

public class LikeDocumentConverter extends DocumentConverter<Like, se.klartext.data.elasticsearch.document.Like> {

    public LikeDocumentConverter(){
        super(like -> se.klartext.data.elasticsearch.document.Like.builder()
                .id(String.valueOf(like.getId()))
                .createdAt(like.getCreatedAt())
                .user(new UserDocumentConverter().convertFromEntity(like.getUser()))
                .build()
        );
    }
}
