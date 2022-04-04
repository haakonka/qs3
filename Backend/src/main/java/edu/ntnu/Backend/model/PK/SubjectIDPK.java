package edu.ntnu.Backend.model.PK;

import java.io.Serializable;

/**
 * This class is made as a representation of the multiple composite primary keys in the database.
 * It contains necessary access methods.
 * This class also has an equal method for comparing instances of similar primary keys from the database.
 */
public class SubjectIDPK implements Serializable {
    private String subjectCode;
    private int schoolYear;

    public SubjectIDPK(){
    }

    public String getSubjectCode() {return subjectCode;}

    public void setSubjectCode(String subjectCode) {this.subjectCode = subjectCode;}

    public int getSchoolYear() {return schoolYear;}

    public void setSchoolYear(int schoolYear) {this.schoolYear = schoolYear;}

    public boolean equals(Object obj){
        if (obj == this) return true;
        if (!(obj instanceof UserSubjectPK)) return false;
        if (obj == null) return false;
        SubjectIDPK pk = (SubjectIDPK) obj;
        return pk.schoolYear == schoolYear && pk.subjectCode.equals(subjectCode);
    }
}
