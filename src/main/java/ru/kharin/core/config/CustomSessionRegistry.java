package ru.kharin.core.config;

import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Service;

@Service("sessionRegistry")
public class CustomSessionRegistry extends SessionRegistryImpl {
}
