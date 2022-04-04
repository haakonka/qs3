package edu.ntnu.Backend.model.DTO;

/**
 * A class made so the token of a user could be transferred.
 * This class contains necessary access methods.
 * The get method has formatting due to how the given format is.
 * This class only has one field called token.
 */
public class TokenDTO {
    String token;

    public String getToken() {
        return token.replace("\"", "");
    }

    public void setToken(String token) {
        this.token = token;
    }
}
