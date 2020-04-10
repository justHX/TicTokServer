package ru.kharin.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kharin.db.entity.Users;


public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByLoginIgnoreCase(String login);
}
