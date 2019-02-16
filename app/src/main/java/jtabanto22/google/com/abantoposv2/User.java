package jtabanto22.google.com.abantoposv2;

public class User {

    String fName;
    String lName;
    String birthdate;
    String email;


    public User(){}

    public User(String fName, String lName, String birthdate, String email) {
        this.fName = fName;
        this.lName = lName;
        this.birthdate = birthdate;
        this.email = email;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
