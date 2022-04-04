package edu.ntnu.Backend.model.DTO;

/**
 * A class that functions as a container containing certain data.
 * The format for said data is token and userID.
 * This class has no constructor due to it being a data transfer object.
 * This class contains necessary access methods for the variable it contains.
 */
public class UserbyIdDTO {
    String token;
    int userID;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
