package se.klartext.app.data.elasticsearch.converter;

import se.klartext.app.data.jpa.entity.Like;
import se.klartext.app.data.elasticsearch.document.LikeDocument;

public class LikeDocumentConverter extends DocumentConverter<Like,LikeDocument> {

    public LikeDocumentConverter(){
        super(like -> LikeDocument.builder()
                .id(String.valueOf(like.getId()))
                .createdAt(like.getCreatedAt())
                .userDocument(new UserDocumentConverter().convertFromEntity(like.getUser()))
                .build()
        );
    }
}
