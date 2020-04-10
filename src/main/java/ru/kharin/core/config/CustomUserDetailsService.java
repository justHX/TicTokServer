package ru.kharin.core.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kharin.core.data.CustomUserDetailsData;
import ru.kharin.db.entity.Users;
import ru.kharin.db.repository.UserRepository;

/**
 * Implementation UserDetailService for Spring Security
 *
 * @author George Beliy on 10-01-2020
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

        return new CustomUserDetailsData(user, true, true, true, !user.isBlocked());
    }

}
