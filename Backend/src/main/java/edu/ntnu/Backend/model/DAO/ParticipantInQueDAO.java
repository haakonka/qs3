package edu.ntnu.Backend.model.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "participantinque", schema = "qsusers")
public class ParticipantInQueDAO implements Serializable {
    @Id
    @Column(name = "participantinque_id")
    private int participantInQueID;

    @Column(name = "user_id")
    private int userID;

    @Column(name = "subject_code")
    private String subjectCode;

    @Column(name = "school_year")
    private int schoolYear;

    @Column(name = "assignment_number")
    private int assignmentNumber;

    @Column(name = "joined_que")
    private Timestamp joinedQue;

    public ParticipantInQueDAO(int participantInQueID, int userID, String subjectCode, int schoolYear, int assignmentNumber, Timestamp joinedQue) {
        this.participantInQueID = participantInQueID;
        this.userID = userID;
        this.subjectCode = subjectCode;
        this.schoolYear = schoolYear;
        this.assignmentNumber = assignmentNumber;
        this.joinedQue = joinedQue;
    }

    public ParticipantInQueDAO(int userID, String subjectCode, int schoolYear, int assignmentNumber, Timestamp joinedQue) {
        this.userID = userID;
        this.subjectCode = subjectCode;
        this.schoolYear = schoolYear;
        this.assignmentNumber = assignmentNumber;
        this.joinedQue = joinedQue;
    }

    public ParticipantInQueDAO(){
    }

    public int getParticipantInQueID() {
        return participantInQueID;
    }

    public void setParticipantInQueID(int participantInQueID) {
        this.participantInQueID = participantInQueID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public int getAssignmentNumber() {
        return assignmentNumber;
    }

    public void setAssignmentNumber(int assignmentNumber) {
        this.assignmentNumber = assignmentNumber;
    }

    public Timestamp getJoinedQue() {
        return joinedQue;
    }

    public void setJoinedQue(Timestamp joinedQue) {
        this.joinedQue = joinedQue;
    }
}
