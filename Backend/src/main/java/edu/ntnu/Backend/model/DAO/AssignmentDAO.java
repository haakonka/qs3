package edu.ntnu.Backend.model.DAO;

import edu.ntnu.Backend.model.PK.AssignmentPK;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class function as a representation of the table assignment from the database.
 * This class contains the same fields as the columns from the database.
 * Access methods for these values is also contained in this class.
 */
@Entity
@IdClass(AssignmentPK.class)
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

    public AssignmentDAO() {

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
