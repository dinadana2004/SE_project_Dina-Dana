package presentation;
import domain.Admin;
import java.util.Scanner;

import service.LoginChecking;




public class EnterNamePassword {
	public static void main(String[]argument) {
		Admin admin=new Admin("Dina","1234");
		LoginChecking check=new LoginChecking(admin);
		 Scanner scanner = new Scanner(System.in);
		 System.out.print("Enter username:");
		 String name=scanner.nextLine();
		 
		 System.out.print("Enter password:");
		 String password=scanner.nextLine();
		 
		 String result= check.checking(name, password);
		 System.out.println(result);
		 String result2=check.trying_to_logout();
		 System.out.println(result2);
		 
		 
		 scanner.close();
	}

}
