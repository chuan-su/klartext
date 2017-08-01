package se.klartext.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import org.springframework.web.filter.GenericFilterBean;
import se.klartext.app.security.api.AuthenticationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class TokenAuthenticationFilter extends GenericFilterBean {

    private static final String HEADER_TOKEN = "X-Auth-Token";

    private final AuthenticationService authenticationService;

    @Autowired
    public TokenAuthenticationFilter(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;

        Optional<String> securityToken = Optional.ofNullable(httpRequest.getHeader(HEADER_TOKEN));

        securityToken.ifPresent(token -> {
            authenticationService.authenticateWithToken(token);
        });

        chain.doFilter(request,response);
    }
}
