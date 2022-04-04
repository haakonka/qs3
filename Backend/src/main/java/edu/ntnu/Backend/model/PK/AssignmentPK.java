package edu.ntnu.Backend.model.PK;

import java.io.Serializable;
import java.util.Objects;

public class AssignmentPK implements Serializable {
    int assignmentNumber;
    String subjectCode;
    int schoolYear;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentPK that = (AssignmentPK) o;
        return assignmentNumber == that.assignmentNumber && schoolYear == that.schoolYear && Objects.equals(subjectCode, that.subjectCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentNumber, subjectCode, schoolYear);
    }
}
