package se.klartext.app.data.elasticsearch.converter;

import se.klartext.app.data.jpa.entity.User;
import se.klartext.app.data.elasticsearch.document.UserDocument;

public class UserDocumentConverter extends DocumentConverter<User,UserDocument> {

    public UserDocumentConverter(){

        super(user -> UserDocument.builder()
                .id(String.valueOf(user.getId()))
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .build());
    }
}
