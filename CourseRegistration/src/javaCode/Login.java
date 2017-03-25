package javaCode;

//import java.sql.*;
import java.util.*;

public class Login {

	public static void startPage(Scanner ip) {
		System.out.println("*****Course Registration System*****");

		while (true) {
			System.out.println("1. Admin Login");
			System.out.println("2. Student Login");
			System.out.println("3. Exit");
			System.out.print("Choice : ");
			int choice = ip.nextInt();
			System.out.println("Your choice is: " + choice);
		}
	}
}
