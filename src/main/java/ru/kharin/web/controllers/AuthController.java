package ru.kharin.web.controllers;

import com.reksoft.core.configuration.AuthenticationService;
import com.reksoft.web.data.auth.LoginCredentials;
import com.reksoft.web.data.employee.EmployeeDTO;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.kharin.core.data.CustomUserDetailsData;
import ru.kharin.web.data.UserDTO;
import ru.kharin.web.helpers.UserHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Api(
        value = "Controller for auth logic.",
        produces = "George Beliy on 23-01-2020"
)
@RestController
public class AuthController {

    public final AuthenticationService authenticationService;
    private final SessionRegistry sessionRegistry;
    private final UserDetailsService userDetailsService;


    public AuthController(AuthenticationService authenticationService, SessionRegistry sessionRegistry,
                          UserDetailsService userDetailsService) {
        this.authenticationService = authenticationService;
        this.sessionRegistry = sessionRegistry;
        this.userDetailsService = userDetailsService;
    }

    @SuppressWarnings("ConstantConditions")
    @PostMapping(value = "/api/login")
    public ResponseEntity<Object> login(@RequestBody LoginCredentials credentials) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        try {
            String sessionId;
            CustomUserDetailsData userDetails;
            HttpSession session = request.getSession(false);
            if (session != null && sessionRegistry.getSessionInformation(session.getId()) != null) {
                sessionId = session.getId();
                userDetails = (CustomUserDetailsData) sessionRegistry.getSessionInformation(sessionId).getPrincipal();
            } else {
                sessionId = authenticationService.auth(credentials.getUsername(), credentials.getPassword());
                userDetails = (CustomUserDetailsData) userDetailsService.loadUserByUsername(credentials.getUsername());
                if (sessionRegistry.getSessionInformation(sessionId) == null) {
                    sessionRegistry.registerNewSession(sessionId, userDetails);
                }
            }
            UserDTO userDTO = new UserDTO();
            userDTO.setEmployee(new EmployeeDTO(userDetails.getUser().getEmployee()));
            userDTO.setPermissions(UserHelper.getUserPermissions());
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @PostMapping("/api/logout")
    public ResponseEntity<String> logout() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(false);
        if (session != null) {
            SessionInformation sessionInformation = sessionRegistry.getSessionInformation(session.getId());
            if (sessionInformation != null) {
                sessionInformation.expireNow();
            }
        }
        return new ResponseEntity<>("{\"message\":\"Successful logout\"}", HttpStatus.OK);
    }

    @GetMapping("/api/currentUser")
    public ResponseEntity<UserDTO> getCurrentUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmployee(new EmployeeDTO(UserHelper.getUser().getEmployee()));
        userDTO.setPermissions(UserHelper.getUserPermissions());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
}
