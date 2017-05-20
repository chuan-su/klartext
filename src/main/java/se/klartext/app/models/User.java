package se.klartext.app.models;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by suchuan on 2017-05-20.
 */
@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue Long id;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    String name,email,password;

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }

}
