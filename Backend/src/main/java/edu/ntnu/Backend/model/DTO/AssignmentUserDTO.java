package edu.ntnu.Backend.model.DTO;

public class AssignmentUserDTO {
    String token;
    String subjectCode;
    String schoolYear;

    public String getToken() {return token.replace("\"", "");}

    public void setToken(String token) {this.token = token;}

    public String getSubjectCode() {return subjectCode.replace("\"","");}

    public void setSubjectCode(String subjectCode) {this.subjectCode = subjectCode;}

    public String getSchoolYear() {return schoolYear.replace("\"","");}

    public void setSchoolYear(String schoolYear) {this.schoolYear = schoolYear;}
}
