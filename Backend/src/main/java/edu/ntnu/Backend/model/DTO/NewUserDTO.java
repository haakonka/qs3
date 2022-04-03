package edu.ntnu.Backend.model.DTO;

public class NewUserDTO {
    String token;
    String lastname;
    String firstname;
    String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
