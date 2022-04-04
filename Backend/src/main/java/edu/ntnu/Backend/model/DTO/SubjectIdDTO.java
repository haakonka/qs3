package edu.ntnu.Backend.model.DTO;

/**
 * A class that functions as a container containing certain data.
 * The format for said data is token, subjectCode and schoolYear.
 * This class has no constructor due to it being a data transfer object.
 * This class contains necessary access methods for the variable it contains.
 * All get methods remove \" from the variable due to how the data is formatted.
 */
public class SubjectIdDTO {
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
