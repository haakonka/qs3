package edu.ntnu.Backend.model.DTO;

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
