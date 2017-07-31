package se.klartext.app.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="auth_tokens")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthToken extends BaseEntity {

    private String token;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    private LocalDateTime expiresAt;
}
