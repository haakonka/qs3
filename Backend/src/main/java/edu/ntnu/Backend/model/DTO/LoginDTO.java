package edu.ntnu.Backend.model.DTO;

/**
 * A class made to be able to transfer the user login info.
 * This class should only be uses for login, since it has no password protection.
 * This class also contains necessary access methods.
 * The format needed for this class is username and password.
 */
public class LoginDTO {
    public String username;
    public String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
