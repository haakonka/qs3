package edu.ntnu.Backend.model.DTO;

/**
 * A class that functions as a container containing certain data. This class is for sending a unique id.
 * The format for said data is token and uniqueId.
 * This class has no constructor due to it being a data transfer object.
 * This class contains necessary access methods for the variable it contains.
 * All get methods remove \" from the variable due to how the data is formatted.
 */
public class UniqueIdDTO {
    String token;
    String uniqueId;

    public String getToken() {return token.replace("\"", "");}

    public void setToken(String token) {this.token = token;}

    public String getUniqueId() {return uniqueId.replace("\"", "");}

    public void setUniqueId(String uniqueId) {this.uniqueId = uniqueId;}
}
