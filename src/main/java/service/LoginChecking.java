package service;
/**
 * this class handles the operation of loging in and loging out for the adminstartion  
 * This class handles the verification of the entered username and password and return successful login or invalid,uncorrect username or password
 * 
 * @author Dina
 * @version 1.0
 */

import domain.Admin;

public class LoginChecking {
	private Admin admin;
	
	/**
	 * constructs an admin
	 * @param admin the Admin object to manage login/logout operations*/
	public LoginChecking(Admin admin) {
		this.admin=admin;
	}
	
	/**
	 * this function calls the verifyingLogin function in order to return successful login  if the entered username and password are correct or invalid,uncorrect username or password 
	 * @return  successful login or invalid, uncorrect username or password 
	  */
	
	public String checking(String username,String password) {
		if (admin.verifyingLogin(username,password)) {
			
			return"successful login";
		}
		else {
			return "invalid,uncorrect username or password";
		}
	}
	
	/**
	 * this funcyion return Logout Successfully if the administrator loged out or  you are already loged out
	 * @return confirmation message for logout result */
	  public String trying_to_logout() {
		  if(admin.is_loged()) {
			  admin.logout();
			  return"Logout Successfully";
		  }
		  else {
			  return"you are already loged out";
					  
		  }
		
	  }

}
