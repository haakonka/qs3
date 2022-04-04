package edu.ntnu.Backend.model.DTO;

/**
 * A class made to represent the data needed for the creation of a new user.
 * The has no constructor as it is only meant to transfer the data, not store it.
 * This class has necessary access methods.
 * The format needed for this class is token, lastname, firstname, email, subjectCode and subjectYear.
 */
public class NewUserDTO {
    String token;
    String lastname;
    String firstname;
    String email;
    String subjectCode;
    String subjectYear;

    public String getToken() {return token.replace("\"", "");}

    public void setToken(String token) {
        this.token = token;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getSubjectYear() {
        return Integer.parseInt(subjectYear);
    }

    public void setSubjectYear(String subjectYear) {
        this.subjectYear = subjectYear;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
