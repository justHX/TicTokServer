package ru.kharin.core.data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import ru.kharin.db.entity.Users;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetailsData extends User {

    private Users user;

    private static final long serialVersionUID = 1905122041950251207L;

    public CustomUserDetailsData(Users user, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                                 boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(user.getLogin(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
        //this.allPermissionsKeys = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
    }

    public Users getUser() {
        return user;
    }
}
