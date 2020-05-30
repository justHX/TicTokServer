package ru.kharin.core.config;


import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kharin.core.data.CustomUserDetailsData;
import ru.kharin.core.domain.Role;
import ru.kharin.db.entity.Users;
import ru.kharin.db.repository.UserRepository;

/**
 * Implementation UserDetailService for Spring Security
 */
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Users user = userRepository.findByLoginIgnoreCase(login);

        if (user == null)
            throw new UsernameNotFoundException("Login" + login + " doesn't exist!");

        return new CustomUserDetailsData(user, true, true, true, !user.isBlocked(), ImmutableList.of(Role.USER));
    }

}
