package edu.ntnu.Backend.model.DTO;

/**
 * A class that functions as a container containing certain data.
 * The format for said data is token, participantInQueId and statusChange.
 * This class has no constructor due to it being a data transfer object.
 * This class contains necessary access methods for the variable it contains.
 * All get methods remove \" from the variable due to how the data is formatted.
 */
public class ParticipantStatusDTO {
    String token;
    String participantInQueId;
    String statusChange;

    public String getToken() {return token.replace("\"", "");}

    public int getParticipantInQueId() {return Integer.valueOf(participantInQueId.replace("\"", ""));}

    public int getStatusChange() {return Integer.valueOf(statusChange.replace("\"", ""));}

    public void setToken(String token) {this.token = token;}

    public void setParticipantInQueId(String participantInQueId) {this.participantInQueId = participantInQueId;}

    public void setStatusChange(String statusChange) {this.statusChange = statusChange;}
}
