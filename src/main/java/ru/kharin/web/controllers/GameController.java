package ru.kharin.web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kharin.db.entity.Users;
import ru.kharin.db.repository.UserAchievementRepository;
import ru.kharin.db.repository.UserRepository;
import ru.kharin.web.data.UserDTO;
import ru.kharin.web.data.UserRegDTO;



@RestController
@RequestMapping
public class GameController {

    private final UserRepository userRepository;
    private final UserAchievementRepository userAchievementRepository;

    @Autowired
    public GameController(UserRepository userRepository, UserAchievementRepository userAchievementRepository) {
        this.userRepository = userRepository;
        this.userAchievementRepository = userAchievementRepository;
    }

    @GetMapping("/testing/{login}")
    public ResponseEntity<String> getEmployee(@PathVariable String login) {
        System.out.println(userRepository.findAll());
        return new ResponseEntity<>(login, HttpStatus.OK);
    }

    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    public ResponseEntity checkUser(@RequestBody UserDTO userDTO) {
        for (Users u : userRepository.findAll()) {
            if (u.getLogin().equals(userDTO.getLogin()) && u.getPassword().equals(userDTO.getPassword()))
                return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity regUser(@RequestBody UserRegDTO userRegDTO) {
        for (Users u : userRepository.findAll()) {
            if (u.getLogin().equals(userRegDTO.getLogin()))
                return new ResponseEntity(HttpStatus.IM_USED);
        }
        userRepository.save(new Users(userRegDTO.getLogin(), userRegDTO.getPassword(), userRegDTO.getNickname()));
        return new ResponseEntity(HttpStatus.OK);
    }


}
