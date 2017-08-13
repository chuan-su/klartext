package se.klartext.app.lib.converter;

import se.klartext.app.model.Like;
import se.klartext.app.model.elasticsearch.LikeDocument;

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
