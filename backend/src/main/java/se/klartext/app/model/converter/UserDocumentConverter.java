package se.klartext.app.model.converter;

import se.klartext.app.model.User;
import se.klartext.app.model.converter.DocumentConverter;
import se.klartext.app.model.elasticsearch.UserDocument;

public class UserDocumentConverter extends DocumentConverter<User,UserDocument> {

    public UserDocumentConverter(){

        super(user -> UserDocument.builder()
                .id(user.getId().intValue())
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .build());
    }
}
