package se.klartext.app.security.impl;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import se.klartext.app.model.User;

import java.util.Collection;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {

    @Getter
    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o){
        return this == o
                || o != null && o instanceof UserDetailsImpl
                && Objects.equals(user, ((UserDetailsImpl) o).user);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(user);
    }
}
