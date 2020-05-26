package com.course.security;

import com.course.security.jwt.JwtSecurityConfigurer;
import com.course.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SpringSecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/signIn").permitAll()
                .antMatchers(HttpMethod.GET, "/*/all").permitAll()
                .antMatchers(HttpMethod.POST, "/*/add").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/*/update").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/*/deleteById").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/*/deleteByName").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/*/getBy*Id").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/*/getBy*Name").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }
}
