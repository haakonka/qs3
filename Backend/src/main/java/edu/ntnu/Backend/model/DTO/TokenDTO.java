package edu.ntnu.Backend.model.DTO;

public class TokenDTO {
    String token;

    public String getToken() {
        return token.replace("\"", "");
    }

    public void setToken(String token) {
        this.token = token;
    }
}
