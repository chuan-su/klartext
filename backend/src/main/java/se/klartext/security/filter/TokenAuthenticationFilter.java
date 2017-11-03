package se.klartext.security.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.filter.GenericFilterBean;
import se.klartext.security.api.AuthenticationService;

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

        try{
            String securityToken  = Optional.ofNullable(httpRequest.getHeader(HEADER_TOKEN))
                    .orElseThrow(() -> new MissingServletRequestParameterException(HEADER_TOKEN,"String"));

            authenticationService.authenticateWithToken(securityToken);
        }catch (MissingServletRequestParameterException | AuthenticationException e){
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request,response);
    }
}
