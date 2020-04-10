package ru.kharin.web.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.kharin.db.entity.Users;



@Component
public class UserHelper {

    public static Users getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (userIsLogin(auth)) {
            Object principal = auth.getPrincipal();
            if (principal instanceof Users) {
                return (Users) principal;
            }
        }
        throw new RuntimeException("Not have a user in the session");
    }

    /**
     * @return {true} if user is login on the site
     */
    private static boolean userIsLogin(Authentication auth) {
        return auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName());
    }
}
