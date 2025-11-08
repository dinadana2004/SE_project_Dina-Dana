
import domain.Admin;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


import service.LoginChecking;
public class LogingCheckingTest {
	@Test
    public void testValidLogin_case1() {
        Admin admin = new Admin("Dina", "1234");
        LoginChecking service = new LoginChecking(admin);
        assertEquals("Login Successful", service.checking("admin", "1234"));
    }
	
	@Test
	public void tsetValidLogin_case2() {
		Admin admin=new Admin("Dana","1234");
		LoginChecking service = new LoginChecking(admin);
		assertEquals("invalid,uncorrect username or password",service.checking("Dana","1234"));
		
	}
	
	@Test
	public void tsetValidLogin_case3() {
		Admin admin=new Admin("Dina","1231");
		LoginChecking service = new LoginChecking(admin);
		assertEquals("invalid,uncorrect username or password",service.checking("Dina","1231"));
		
	}
	
	@Test
	public void tsetValidLogin_case4() {
		Admin admin=new Admin("Dana","1231");
		LoginChecking service = new LoginChecking(admin);
		assertEquals("invalid,uncorrect username or password",service.checking("Dana","1231"));
		
	}
	
	
	
}


