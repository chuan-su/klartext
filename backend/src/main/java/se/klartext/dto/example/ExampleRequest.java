package se.klartext.dto.example;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
public class ExampleRequest {

    @NotNull
    private String body;

    private String translation;
}
