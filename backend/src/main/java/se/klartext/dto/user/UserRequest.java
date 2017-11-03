package se.klartext.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
public class UserRequest {

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private  String password;
}
