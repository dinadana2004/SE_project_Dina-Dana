package domain;

/**
 * Represents an administrator who can log in and manage the library system.
 * This class handles login verification and logout functionality.
 * 
 * @author Dina
 * @version 1.0
 */

public class Admin {
	private String username;
	private String password;
	private boolean loged;
	
	 /**
     * Constructs an Admin with the specified username and password.
     *
     * @param username the administrator's username
     * @param password the administrator's password
     */
	public Admin (String username,String password) {
		this.username=username;
		this.password=password;
		this.loged=false;
	}
	
	/**
	 * THIS FUNTION IS USED TO VERIFY THE USERENAME AND PASSWORD the person has entered
	 * @param enteredname which is the the username of the adminstartor we want to check it whether it is correct or not
	 * @param  enteredpassword which is the the password of the adminstartor we want to check it whether it is correct or not
	 * @return true if the credentials are correct and the admin is loged in, otherwise return false and admin is not loged in*/
	
	public boolean verifyingLogin(String enteredname,String enteredpassword) {
		if( username.equals(enteredname) && password.equals(enteredpassword)) {
			loged=true;
			return true;
		}
		else{
			return false;
		}
		
	}
	
	/**
	 * this funtion set loged to false in indication to that this  admin has loged out and he need to relogin if this admin want to do any service */
	
	public void logout(){
		loged=false;
	}
	
	/**
	 * this function checks wheter the admin is loged in or not 
	 * @return true if loged is true otherwise return false*/
	
	
	public boolean is_loged() {
		return loged;
	}
	
	
}
