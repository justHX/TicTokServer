package ru.kharin.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by  Nikita Kharin
 */
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @SuppressWarnings("ConstantConditions")
    public String auth(String login, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, password);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String sessionId = request.getSession().getId();
        request.getSession().setMaxInactiveInterval(Integer.MAX_VALUE);
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication result = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(result);
        return sessionId;
    }
}
