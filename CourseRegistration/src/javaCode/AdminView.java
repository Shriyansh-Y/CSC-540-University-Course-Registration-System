package javaCode;

import java.util.Scanner;

public class AdminView {

	// Method to enroll students
	public static void enrollStudent(Scanner ip){
		
	}
	
	// Method to view student details
	public static void viewStudentDetails(Scanner ip){
		
	}
	
	// Method to view admin profile
	public static void viewProfile(Scanner ip){
		while(true){
			System.out.println("**Your Profile**");
			System.out.println("Press 0 to go back.");
			System.out.println("First Name: " + AdminProfile.getInstance().getFirstname());
			System.out.println("Last Name: " + AdminProfile.getInstance().getLastname());
			System.out.println("Date of Birth: " + AdminProfile.getInstance().getDob());
			System.out.println("Employee id: " + AdminProfile.getInstance().getEid());
		
			int n = ip.nextInt();
			if(n == 0){
				Login.admin_homepage(ip);
			}
			else{
				System.out.println("Please enter 0 to go back.");
			}
		}
	}
	
	// Method to view/add courses
	public static void viewaddCourses(Scanner ip){
		
	}
	
	// Method to view/add Course offering
	public static void viewaddCourseOffering(Scanner ip){
		
	}
	
}
