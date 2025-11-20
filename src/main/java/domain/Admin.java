package domain;


/**
* Represents an administrator who can log in and manage the library system.
*/
public class Admin {
private String username;
private String password;
private boolean logged;


public Admin(String username, String password) {
this.username = username;
this.password = password;
this.logged = false;
}


public boolean verifyLogin(String enteredName, String enteredPassword) {
if (this.username.equals(enteredName) && this.password.equals(enteredPassword)) {
this.logged = true;
return true;
}
return false;
}


public void logout() { this.logged = false; }


public boolean isLogged() { return this.logged; }


public String getUsername() { return username; }
}