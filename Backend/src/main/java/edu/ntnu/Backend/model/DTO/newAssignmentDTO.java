package edu.ntnu.Backend.model.DTO;

public class newAssignmentDTO {

    String token;
    String subjectCode;
    String schoolYear;
    int assignmentNumber;
    int intervalStart;
    int intervalEnd;
    int minAssignments;

    public int getAssignmentNumber() {
        return assignmentNumber;
    }

    public void setAssignmentNumber(int assignmentNumber) {
        this.assignmentNumber = assignmentNumber;
    }

    public int getIntervalStart() {
        return intervalStart;
    }

    public void setIntervalStart(int intervalStart) {
        this.intervalStart = intervalStart;
    }

    public int getIntervalEnd() {
        return intervalEnd;
    }

    public void setIntervalEnd(int intervalEnd) {
        this.intervalEnd = intervalEnd;
    }

    public int getMinAssignments() {
        return minAssignments;
    }

    public void setMinAssignments(int minAssignments) {
        this.minAssignments = minAssignments;
    }

    public String getToken() {return token.replace("\"", "");}

    public void setToken(String token) {this.token = token;}

    public String getSubjectCode() {return subjectCode.replace("\"","");}

    public void setSubjectCode(String subjectCode) {this.subjectCode = subjectCode;}

    public String getSchoolYear() {return schoolYear.replace("\"","");}

    public void setSchoolYear(String schoolYear) {this.schoolYear = schoolYear;}


}
