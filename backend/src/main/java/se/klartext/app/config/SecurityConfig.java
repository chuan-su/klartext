package se.klartext.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import se.klartext.app.data.jpa.repository.api.AuthTokenRepository;
import se.klartext.app.security.filter.TokenAuthenticationFilter;
import se.klartext.app.security.api.AuthenticationService;
import se.klartext.app.security.impl.AuthenticationServiceImpl;
import se.klartext.app.security.impl.AuthenticationUserDetailsServiceImpl;
import se.klartext.app.security.impl.UserDetailsServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthTokenRepository authTokenRepo;

    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring()
                .antMatchers(HttpMethod.POST,"/api/users/register")
                .antMatchers(HttpMethod.PUT,"/api/users/auth")
                .antMatchers(HttpMethod.GET,"/api/words/search")
                .antMatchers(HttpMethod.GET,"/api/examples/search")
                .antMatchers(HttpMethod.GET,"/api/users/{\\d+}/examples/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(new TokenAuthenticationFilter(authenticationService()), BasicAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthProvider())
                .authenticationProvider(preAuthAuthProvider());
    }

    @Bean(name = "preAuthProvider")
    public AuthenticationProvider preAuthAuthProvider() throws Exception {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(authenticationUserDetailsService());
        return provider;
    }
    @Bean(name = "daoAuthProvider")
    public AuthenticationProvider daoAuthProvider() throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationService authenticationService() throws Exception {
        return new AuthenticationServiceImpl(authenticationManager(),authTokenRepo);
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> authenticationUserDetailsService() {
        return new AuthenticationUserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));

        /*
         * setAllowCredentials(true) is important, otherwise:
         * The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when
         * the request's credentials mode is 'include'.
         */
        configuration.setAllowCredentials(true);

        /*
         * setAllowedHeaders is important! Without it, OPTIONS preflight request will fail with 403 Invalid CORS request
         */
        configuration.setAllowedHeaders(Arrays.asList("Authorization","X-Auth-Token","Cache-Control", "Content-Type","Accept"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }
}
