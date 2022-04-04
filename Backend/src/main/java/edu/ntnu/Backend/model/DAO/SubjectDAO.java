package edu.ntnu.Backend.model.DAO;

import edu.ntnu.Backend.model.PK.SubjectIDPK;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class function as a representation of the table subject from the database.
 * This class contains the same fields as the columns from the database.
 * Access methods for these values is also contained in this class.
 */
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
    private String subjectName;

    @Column(name = "status_que")
    private int statusQue;

    public SubjectDAO(String subjectCode, int schoolYear, String subjectName, int statusQue) {
        this.subjectCode = subjectCode;
        this.schoolYear = schoolYear;
        this.subjectName = subjectName;
        this.statusQue = statusQue;
    }

    public SubjectDAO(){
    }

    public String getSubjectCode() {return subjectCode;}

    public void setSubjectCode(String subjectCode) {this.subjectCode = subjectCode;}

    public int getSchoolYear() {return schoolYear;}

    public void setSchoolYear(int schoolYear) {this.schoolYear = schoolYear;}

    public int getStatusQue() {return statusQue;}

    public void setStatusQue(int statusQue) {this.statusQue = statusQue;}

    public String getSubjectName() {return subjectName;}

    public void setSubjectName(String subjectName) {this.subjectName = subjectName;}
}
