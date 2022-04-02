package edu.ntnu.Backend.model.PK;

import java.io.Serializable;

public class QuePK implements Serializable {
    private String subjectCode;
    private int schoolYear;

    public QuePK(){
    }

    public String getSubjectCode() {return subjectCode;}

    public void setSubjectCode(String subjectCode) {this.subjectCode = subjectCode;}

    public int getSchoolYear() {return schoolYear;}

    public void setSchoolYear(int schoolYear) {this.schoolYear = schoolYear;}

    public boolean equals(Object obj){
        if (obj == this) return true;
        if (!(obj instanceof UserSubjectPK)) return false;
        if (obj == null) return false;
        QuePK pk = (QuePK) obj;
        return pk.schoolYear == schoolYear && pk.subjectCode.equals(subjectCode);
    }
}
