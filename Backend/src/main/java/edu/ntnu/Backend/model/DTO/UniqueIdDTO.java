package edu.ntnu.Backend.model.DTO;

public class UniqueIdDTO {
    String token;
    String uniqueId;

    public String getToken() {return token.replace("\"", "");}

    public void setToken(String token) {this.token = token;}

    public String getUniqueId() {return uniqueId.replace("\"", "");}

    public void setUniqueId(String uniqueId) {this.uniqueId = uniqueId;}
}
