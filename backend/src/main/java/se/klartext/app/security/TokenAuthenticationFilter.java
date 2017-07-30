package se.klartext.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import se.klartext.app.security.api.AuthenticationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

        String token = httpRequest.getHeader(HEADER_TOKEN);

        if(authenticationService.isValidToken(token)){
            chain.doFilter(request,response);
        }else{
            HttpServletResponse httpResponse = (HttpServletResponse)response;
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"invalid token");
        }
    }
}
