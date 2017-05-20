package se.klartext.app.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by suchuan on 2017-05-20.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "users")
public class User extends BaseEntity{

    private String name,email,password;
}
