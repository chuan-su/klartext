package se.klartext.converter;

import se.klartext.domain.model.user.User;

public class UserDocumentConverter extends DocumentConverter<User, se.klartext.data.elasticsearch.document.User> {

    public UserDocumentConverter(){

        super(user -> se.klartext.data.elasticsearch.document.User.builder()
                .id(String.valueOf(user.getId()))
                .name(user.getName())
                .createdAt(user.getCreatedAt())
                .build());
    }
}
