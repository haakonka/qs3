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
    @Column(name = "assignmentUserID")
    private int assignmentUserID;

    @Column(name = "userID")
    private int userId;

    @Column(name = "subjectCode")
    private String subjectCode;

    @Column(name = "schoolYear")
    private int schoolYear;

    @Column(name = "assignmentNumber")
    private int assignmentNumber;

    @Column(name = "status")
    private int status;

    public AssignmentUserDAO(int assignmentUserID, int userId, String subjectCode, int schoolYear, int assignmentNumber, int status) {
        this.assignmentUserID = assignmentUserID;
        this.userId = userId;
        this.subjectCode = subjectCode;
        this.schoolYear = schoolYear;
        this.assignmentNumber = assignmentNumber;
        this.status = status;
    }

    public AssignmentUserDAO(){
    }

    public int getAssignmentUserID() {return assignmentUserID;}

    public void setAssignmentUserID(int assignmentUserID) {this.assignmentUserID = assignmentUserID;}

    public int getUserId() {return userId;}

    public void setUserId(int userId) {this.userId = userId;}

    public String getSubjectCode() {return subjectCode;}

    public void setSubjectCode(String subjectCode) {this.subjectCode = subjectCode;}

    public int getSchoolYear() {return schoolYear;}

    public void setSchoolYear(int schoolYear) {this.schoolYear = schoolYear;}

    public int getAssignmentNumber() {return assignmentNumber;}

    public void setAssignmentNumber(int assignmentNumber) {this.assignmentNumber = assignmentNumber;}

    public int getStatus() {return status;}

    public void setStatus(int status) {this.status = status;}
}
