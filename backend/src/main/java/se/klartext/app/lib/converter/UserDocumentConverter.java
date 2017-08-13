package se.klartext.app.lib.converter;

import se.klartext.app.model.User;
import se.klartext.app.model.elasticsearch.UserDocument;

public class UserDocumentConverter extends DocumentConverter<User,UserDocument> {

    public UserDocumentConverter(){

        super(user -> UserDocument.builder()
                .id(String.valueOf(user.getId()))
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .build());
    }
}
