package ru.kharin.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kharin.db.entity.UserAchievement;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
}
