package se.klartext.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import se.klartext.app.data.api.AuthTokenRepository;
import se.klartext.app.security.TokenAuthenticationFilter;
import se.klartext.app.security.api.AuthenticationService;
import se.klartext.app.security.impl.AuthenticationServiceImpl;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthTokenRepository authTokenRepository;

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring()
                .antMatchers(HttpMethod.PUT,"/api/users/auth")
                .antMatchers(HttpMethod.GET,"/api/search/**")
                .antMatchers(HttpMethod.GET,"/api/users/{\\d+}/posts/**");

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(new TokenAuthenticationFilter(authenticationService()), BasicAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationService authenticationService() throws Exception {
        return new AuthenticationServiceImpl(authenticationManagerBean(),authTokenRepository);
    }

    @Bean(name="myAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
