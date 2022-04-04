package edu.ntnu.Backend.model.DTO;

public class ParticipantInQueDTO {
    String token;
    String subjectCode;
    String schoolYear;
    String assignmentNumber;
    String joinedQue;

    public String getToken() {return token.replace("\"", "");}

    public void setToken(String token) {this.token = token;}

    public String getSubjectCode() {return subjectCode.replace("\"", "");}

    public void setSubjectCode(String subjectCode) {this.subjectCode = subjectCode;}

    public String getSchoolYear() {return schoolYear.replace("\"", "");}

    public void setSchoolYear(String schoolYear) {this.schoolYear = schoolYear;}

    public String getAssignmentNumber() {return assignmentNumber.replace("\"", "");}

    public void setAssignmentNumber(String assignmentNumber) {this.assignmentNumber = assignmentNumber;}

    public String getJoinedQue() {return joinedQue.replace("\"", "");}

    public void setJoinedQue(String joinedQue) {this.joinedQue = joinedQue;}
}
