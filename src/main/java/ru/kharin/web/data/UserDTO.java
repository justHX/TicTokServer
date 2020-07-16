package ru.kharin.web.data;

import ru.kharin.db.entity.Users;

import javax.validation.constraints.NotNull;

public class UserDTO {
    private String nickname;
    private Users user;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
