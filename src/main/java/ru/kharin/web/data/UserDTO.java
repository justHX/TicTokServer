package ru.kharin.web.data;

import javax.validation.constraints.NotNull;

public class UserDTO {
   private String login;
   private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
