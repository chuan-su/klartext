package se.klartext.app.models;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by suchuan on 2017-05-20.
 */
@Entity
@Getter
@Table(name = "users")
public class User extends BaseEntity{

    String name,email,password;

}
