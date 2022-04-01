package edu.ntnu.Backend.model.DAO;


import edu.ntnu.Backend.model.PK.UserSubjectPK;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(UserSubjectPK.class)
@Table(name = "usersubjects", schema = "qsusers")
public class UserSubjectDAO implements Serializable {

    @Id
    @Column(name = "schoolYear")
    private int schoolYear;

    @Id
    @Column(name = "subjectCode")
    private String subjectCode;

    @Id
    @Column(name = "userId")
    private int userId;

    public UserSubjectDAO(int schoolYear, String subjectCode, int userId) {
        this.schoolYear = schoolYear;
        this.subjectCode = subjectCode;
        this.userId = userId;
    }

    public UserSubjectDAO() {
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
