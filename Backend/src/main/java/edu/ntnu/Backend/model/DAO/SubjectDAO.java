package edu.ntnu.Backend.model.DAO;

import edu.ntnu.Backend.model.PK.SubjectIDPK;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(SubjectIDPK.class)
@Table(name = "subject", schema = "qsusers")
public class SubjectDAO implements Serializable {
    @Id
    @Column(name = "subject_code")
    private String subjectCode;

    @Id
    @Column(name = "school_year")
    private int schoolYear;

    @Column(name = "subject_name")
    private String statusQue;

    public SubjectDAO(String subjectCode, int schoolYear, String statusQue) {
        this.subjectCode = subjectCode;
        this.schoolYear = schoolYear;
        this.statusQue = statusQue;
    }

    public SubjectDAO(){
    }

    public String getSubjectCode() {return subjectCode;}

    public void setSubjectCode(String subjectCode) {this.subjectCode = subjectCode;}

    public int getSchoolYear() {return schoolYear;}

    public void setSchoolYear(int schoolYear) {this.schoolYear = schoolYear;}

    public String getStatusQue() {return statusQue;}

    public void setStatusQue(String statusQue) {this.statusQue = statusQue;}
}
