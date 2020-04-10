package ru.kharin.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Configuration file for Spring Security
 *
 * @author George Beliy on 10-01-2020
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("ru.kharin")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PAGE = "/pages/auth/loginPage.xhtml";
    private static final String ACCESS_DENIED_PAGE = "/pages/auth/accessDenied.xhtml";
    public static final String SESSION_ATTRIBUTE = "JSESSIONID";

    private final UserDetailsService userDetailsService;
    private final AuthenticationProvider authenticationProvider;
    private final SessionRegistry sessionRegistry;


    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, AuthenticationProvider authenticationProvider,
                          SessionRegistry sessionRegistry) {
        this.userDetailsService = userDetailsService;
        this.authenticationProvider = authenticationProvider;
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                antMatchers("/api/login").permitAll().
                antMatchers("/**").authenticated().
            and().
                formLogin().disable().  //login configuration
            logout().    //logout configuration
                logoutUrl("/appLogout").invalidateHttpSession(true).deleteCookies(SESSION_ATTRIBUTE).
            and().
                sessionManagement().maximumSessions(5).sessionRegistry(sessionRegistry).and().
            and().
                csrf().disable();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider).userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    @SuppressWarnings("EmptyMethod")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
