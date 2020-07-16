package ru.kharin.core.config;

import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import ru.kharin.core.data.CustomUserDetailsData;
import ru.kharin.db.entity.Users;
import ru.kharin.db.repository.UserRepository;

@Service("authenticationProvider")
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final SessionRegistry sessionRegistry;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;


    @Autowired
    public CustomAuthenticationProvider(SessionRegistry sessionRegistry, UserRepository userRepository, UserDetailsService userDetailsService) {
        this.sessionRegistry = sessionRegistry;
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SessionInformation sessionInformation = sessionRegistry
                .getSessionInformation(((WebAuthenticationDetails) authentication.getDetails()).getSessionId());
        UserDetails ud;
        if (sessionInformation == null) {
            try {
                if (authentication.getCredentials() == null || StringUtils.isBlank("" + authentication.getCredentials())) {
                    return null;
                }
                String login = "" + authentication.getPrincipal();

                Users user = userRepository.findByLoginIgnoreCase(login);
                if (user == null) {
                    Users newUser = new Users();
                    newUser.setLogin(login);
                    newUser.setPassword("-");
                    userRepository.save(newUser);
                }
                ud = userDetailsService.loadUserByUsername(login);
            } catch (Exception e) {
                return null;
            }
        } else {
            ud = (UserDetails) sessionInformation.getPrincipal();
            sessionInformation.expireNow();
        }
        return new UsernamePasswordAuthenticationToken(ud, authentication.getCredentials(),
                authentication.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
