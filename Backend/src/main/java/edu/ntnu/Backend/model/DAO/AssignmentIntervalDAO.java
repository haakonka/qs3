package edu.ntnu.Backend.model.DAO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "assignmentinterval", schema = "qsusers")
public class AssignmentIntervalDAO implements Serializable {
    @Id
    @Column(name = "interval_id")
    private int intervalId;

    @Column(name = "subject_code")
    private String subjectCode;

    @Column(name = "school_year")
    private int schoolYear;

    @Column(name = "assignment_number")
    private int assignmentNumber;

    @Column(name = "interval_start")
    private int intervalStart;

    @Column(name = "interval_end")
    private int intervalEnd;

    @Column(name = "min_assignments")
    private int minAssignments;

    public AssignmentIntervalDAO(int intervalId, String subjectCode, int schoolYear, int assignmentNumber, int intervalStart, int intervalEnd, int minAssignments) {
        this.intervalId = intervalId;
        this.subjectCode = subjectCode;
        this.schoolYear = schoolYear;
        this.assignmentNumber = assignmentNumber;
        this.intervalStart = intervalStart;
        this.intervalEnd = intervalEnd;
        this.minAssignments = minAssignments;
    }

    //since id is autoIncrement
    public AssignmentIntervalDAO(String subjectCode, int schoolYear, int assignmentNumber, int intervalStart, int intervalEnd, int minAssignments) {
        this.subjectCode = subjectCode;
        this.schoolYear = schoolYear;
        this.assignmentNumber = assignmentNumber;
        this.intervalStart = intervalStart;
        this.intervalEnd = intervalEnd;
        this.minAssignments = minAssignments;
    }

    public AssignmentIntervalDAO() {
    }

    public int getIntervalId() {return intervalId;}

    public void setIntervalId(int intervalId) {this.intervalId = intervalId;}

    public String getSubjectCode() {return subjectCode;}

    public void setSubjectCode(String subjectCode) {this.subjectCode = subjectCode;}

    public int getSchoolYear() {return schoolYear;}

    public void setSchoolYear(int schoolYear) {this.schoolYear = schoolYear;}

    public int getAssignmentNumber() {return assignmentNumber;}

    public void setAssignmentNumber(int assignmentNumber) {this.assignmentNumber = assignmentNumber;}

    public int getIntervalStart() {return intervalStart;}

    public void setIntervalStart(int intervalStart) {this.intervalStart = intervalStart;}

    public int getIntervalEnd() {return intervalEnd;}

    public void setIntervalEnd(int intervalEnd) {this.intervalEnd = intervalEnd;}

    public int getMinAssignments() {return minAssignments;}

    public void setMinAssignments(int minAssignments) {this.minAssignments = minAssignments;}
}
