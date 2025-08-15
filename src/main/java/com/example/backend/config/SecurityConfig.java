package com.example.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.backend.jwt.filters.JwtRequestFilter;
import com.example.backend.jwt.services.ApplicationUserDetailsService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private  final ApplicationUserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    // This configuration class sets up security for the application.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //We will place the customized userdetailsservice here in the following steps
        auth.userDetailsService(userDetailsService);
    }

    public void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/**").permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
