package edu.ntnu.Backend.model.PK;


import java.io.Serializable;


public class UserSubjectPK implements Serializable {
    private int schoolYear;
    private String subjectCode;
    private int userId;

    public UserSubjectPK() {
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean equals(Object obj){
        if (obj == this) return true;
        if (!(obj instanceof UserSubjectPK)) return false;
        if (obj == null) return false;
        UserSubjectPK pk = (UserSubjectPK) obj;
        return pk.schoolYear == schoolYear && pk.subjectCode.equals(subjectCode) && pk.userId == userId;
    }

}
