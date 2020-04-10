package ru.kharin.core.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .httpBasic()
                .and()
                .csrf().disable();

        http
                .cors();

//        http
//                .authorizeRequests()
//                .antMatchers("http://localhost:4200/", "http://localhost:4200/buttle").permitAll()
//                .anyRequest().authenticated();
//
//        http
//                .formLogin()
//                .loginPage("http://localhost:4200/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();

    }



}
