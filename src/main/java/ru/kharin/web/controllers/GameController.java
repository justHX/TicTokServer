package ru.kharin.web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kharin.db.repository.UserAchievementRepository;
import ru.kharin.db.repository.UserRepository;



@RestController
@RequestMapping
public class GameController {

    @Autowired
    public GameController(UserRepository userRepository, UserAchievementRepository userAchievementRepository) {

    }




}
