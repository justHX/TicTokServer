package ru.kharin.web.data;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(value = "LoginCredentials", description = "Object to login the application")
public class LoginCredentials {

    @ApiModelProperty(value = "Login", required = true)
    private String login;

    @ApiModelProperty(value = "Password", required = true)
    private String password;


    @SuppressWarnings("unused")
    public LoginCredentials() {
    }

    public LoginCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

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
