package se.klartext.app.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import se.klartext.app.data.jpa.repository.api.UserRepository;
import se.klartext.app.data.jpa.entity.User;

public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User: "+ username + "not exist"));

        return new UserDetailsImpl(user);
    }
}
