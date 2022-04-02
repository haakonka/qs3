package edu.ntnu.Backend.model.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


//TODO fix the wrong spelled id so it can access the correct table, gotta check with omar to see which it is
@Entity
@Table(name = "assignmentuser", schema = "qsusers")
public class AssignmentUserDAO implements Serializable {
    @Id
    @Column(name = "assignment_user_id")
    private int assignmentUserID;

    @Column(name = "user_id")
    private int userID;

    @Column(name = "subject_code")
    private String subjectCode;

    @Column(name = "school_year")
    private int schoolYear;

    @Column(name = "assignment_number")
    private int assignmentNumber;

    @Column(name = "status")
    private int status;

    public AssignmentUserDAO(int assignmentUserID, int userID, String subjectCode, int schoolYear, int assignmentNumber, int status) {
        this.assignmentUserID = assignmentUserID;
        this.userID = userID;
        this.subjectCode = subjectCode;
        this.schoolYear = schoolYear;
        this.assignmentNumber = assignmentNumber;
        this.status = status;
    }

    public AssignmentUserDAO(){
    }

    public int getAssignmentUserID() {return assignmentUserID;}

    public void setAssignmentUserID(int assignmentUserID) {this.assignmentUserID = assignmentUserID;}

    public int getUserID() {return userID;}

    public void setUserID(int userId) {this.userID = userId;}

    public String getSubjectCode() {return subjectCode;}

    public void setSubjectCode(String subjectCode) {this.subjectCode = subjectCode;}

    public int getSchoolYear() {return schoolYear;}

    public void setSchoolYear(int schoolYear) {this.schoolYear = schoolYear;}

    public int getAssignmentNumber() {return assignmentNumber;}

    public void setAssignmentNumber(int assignmentNumber) {this.assignmentNumber = assignmentNumber;}

    public int getStatus() {return status;}

    public void setStatus(int status) {this.status = status;}
}
