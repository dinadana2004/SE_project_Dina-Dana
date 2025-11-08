import domain.Admin;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


import service.LoginChecking;

public class loged_out_testing {
	 @Test
	 public void test_logout_case1() {
		 Admin admin=new Admin("Dina","1234");
		 LoginChecking service=new LoginChecking(admin);
		 service.checking("Dina", "1234");
		 String result=service.trying_to_logout();
		 assertEquals("Logout Successfully", result);
		
	 }
	 
	 @Test
	 public void test_logout_case2() {
		 Admin admin=new Admin("Dina","1234");
		 LoginChecking service=new LoginChecking(admin);
		 service.checking("Dina", "1234");
		 String result=service.trying_to_logout();
		 assertEquals("you are already loged out", result);
		
	 }
	 
	 
}
