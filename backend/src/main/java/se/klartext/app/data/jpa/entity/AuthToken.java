package se.klartext.app.data.jpa.entity;

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
@Data
@NoArgsConstructor
public class AuthToken extends BaseEntity {

    private String token;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    private LocalDateTime expiresAt;

    @Builder
    public AuthToken(String token,User user,LocalDateTime expiresAt) {
        this.token = token;
        this.user = user;
        this.expiresAt = expiresAt;
    }
}
