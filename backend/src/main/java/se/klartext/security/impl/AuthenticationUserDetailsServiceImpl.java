package se.klartext.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import se.klartext.domain.model.user.AuthTokenRepository;
import se.klartext.domain.model.user.AuthToken;

public class AuthenticationUserDetailsServiceImpl
        implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

    @Autowired
    private AuthTokenRepository authTokenRepo;

    @Override
    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken preAuthToken) throws UsernameNotFoundException {

        String securityToken = (String) preAuthToken.getPrincipal();

        AuthToken authToken =  authTokenRepo.findByToken(securityToken)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Auth Token: " + preAuthToken.getPrincipal()));

        return new UserDetailsImpl(authToken.getUser());
    }
}
