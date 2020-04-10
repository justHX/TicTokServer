package ru.kharin.core.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import ru.kharin.db.entity.Users;
import ru.kharin.web.helpers.UserHelper;

import java.util.Optional;

@Component
public class SpringSecurityAudit implements AuditorAware<Users> {

    @NonNull
    @Override
    public Optional<Users> getCurrentAuditor() {
        return Optional.of(UserHelper.getUser());
    }
}
