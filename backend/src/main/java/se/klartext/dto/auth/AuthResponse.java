package se.klartext.dto.auth;

import lombok.Data;

@Data
public class AuthResponse {

    private long userId;

    private String userName;

    private String userEmail;

    private String token;
}
