package ru.techmas.simple_login.Events;

/**
 * Created by pavel on 15.01.2017.
 */

public class LoginSeccsessEvent {

    public final String login, password;

    public LoginSeccsessEvent(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
