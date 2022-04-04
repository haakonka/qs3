package edu.ntnu.Backend.model.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "assignment", schema = "qsusers")
public class AssignmentDAO implements Serializable {
    @Id
    @Column(name = "assignment_number")
    private int assignmentNumber;

    @Id
    @Column(name = "subjectCode")
    private String subjectCode;


    @Id
    @Column(name = "schoolYear")
    private int schoolYear;


    public AssignmentDAO(int assignmentNumber, String subjectCode, int schoolYear) {
        this.assignmentNumber = assignmentNumber;
        this.subjectCode = subjectCode;
        this.schoolYear = schoolYear;
    }

    public int getAssignmentNumber() {
        return assignmentNumber;
    }

    public void setAssignmentNumber(int assignmentNumber) {
        this.assignmentNumber = assignmentNumber;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }
}
