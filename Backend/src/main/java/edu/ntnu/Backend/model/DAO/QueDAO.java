package edu.ntnu.Backend.model.DAO;

import edu.ntnu.Backend.model.PK.QuePK;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(QuePK.class)
@Table(name = "que", schema = "qsusers")
public class QueDAO implements Serializable {
    @Id
    @Column(name = "subject_code")
    private String subjectCode;

    @Id
    @Column(name = "school_year")
    private int schoolYear;

    @Column(name = "status_que")
    private int statusQue;

    public QueDAO(String subjectCode, int schoolYear, int statusQue) {
        this.subjectCode = subjectCode;
        this.schoolYear = schoolYear;
        this.statusQue = statusQue;
    }

    public QueDAO(){
    }

    public String getSubjectCode() {return subjectCode;}

    public void setSubjectCode(String subjectCode) {this.subjectCode = subjectCode;}

    public int getSchoolYear() {return schoolYear;}

    public void setSchoolYear(int schoolYear) {this.schoolYear = schoolYear;}

    public int getStatusQue() {return statusQue;}

    public void setStatusQue(int statusQue) {this.statusQue = statusQue;}
}
