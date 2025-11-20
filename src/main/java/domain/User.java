package domain;


/**
* Represents a library user (borrower).
*/
public class User {
private String username;
private double fineBalance = 0.0;


public User(String username) {
this.username = username;
}


public String getUsername() { return username; }


public double getFineBalance() { return fineBalance; }


public void addFine(double amount) { fineBalance += amount; }


public void payFine(double amount) {
fineBalance -= amount;
if (fineBalance < 0) fineBalance = 0;
}


public boolean hasUnpaidFines() { return fineBalance > 0.0; }
}