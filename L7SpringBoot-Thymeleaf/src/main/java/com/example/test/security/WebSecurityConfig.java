package com.example.test.security;

import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userService;

    @Autowired
    private CustomSuccessHandler customSuccessHandler;


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests().antMatchers("/", "/login", "/static/**","/rest/**").permitAll();
        http.authorizeRequests().antMatchers("/user/welcome").access("hasAnyRole('USER', 'ADMIN')");
        http.authorizeRequests().antMatchers("/user/only").access("hasAnyRole('USER')");
        http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
        http.authorizeRequests().and().formLogin()//
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/login")//
                .successHandler(customSuccessHandler)
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")

                // Config for Logout Page
                .and().rememberMe()
                .and().logout();
        http.exceptionHandling().accessDeniedPage("/403");
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder)
            throws Exception {
        builder.userDetailsService(userService).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public OAuth20Service oAuth20Service(){
        return  new ServiceBuilder("8557833449-mgqon77b3gturt3debee5i4rsts1e1lr.apps.googleusercontent.com")
                .apiSecret("IBhDFvBzmJ66tiO8CMPuBZCA")
                .defaultScope("profile")
                .callback("http://localhost:8080/oauth2")
                .build(GoogleApi20.instance());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}