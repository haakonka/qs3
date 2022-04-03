package edu.ntnu.Backend.model.DTO;

public class AssignmentUserDTO {
    String token;
    String assignmentUserId;

    public String getToken() {return token.replace("\"", "");}

    public void setToken(String token) {this.token = token;}

    public String getAssignmentUserId() {return assignmentUserId.replace("\"", "");}

    public void setAssignmentUserId(String assignmentUserId) {this.assignmentUserId = assignmentUserId;}
}
