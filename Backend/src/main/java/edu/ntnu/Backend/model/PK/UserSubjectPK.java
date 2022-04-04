package edu.ntnu.Backend.model.PK;


import java.io.Serializable;

/**
 * This class is made as a representation of the composite primary key in the userSubject table in the database.
 * It contains necessary access methods.
 * This class also has an equal method for comparing instances of primary keys from the userSubject table in the database.
 */
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
