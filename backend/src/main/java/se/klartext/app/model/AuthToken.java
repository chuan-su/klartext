package se.klartext.app.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import se.klartext.app.lib.serializer.LocalDateTimeSerializer;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="auth_tokens")
@Builder @Getter @Setter
public class AuthToken extends BaseEntity {

    private String token;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime expiresAt;
}
