package io.yodo.whisper.auth;

import io.yodo.whisper.commons.security.jwt.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final RequestMatcher secureURLs = new AndRequestMatcher(
            new AntPathRequestMatcher("/**"),
            new NegatedRequestMatcher(new OrRequestMatcher(
                new AntPathRequestMatcher("/actuator/**"),
                new AntPathRequestMatcher("/token")
            ))
    );

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/")
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .exceptionHandling()
            .and()
                .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests()
            .requestMatchers(secureURLs)
                .authenticated()
            .and()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .httpBasic().disable();
    }

    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter filter = new AuthenticationFilter(secureURLs);
        filter.setAuthenticationManager(authenticationManager());
        logger.debug("Created authentication filter " + filter);
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager(TokenDecoder decoder) {
        return new AuthenticationManager(decoder);
    }
}
