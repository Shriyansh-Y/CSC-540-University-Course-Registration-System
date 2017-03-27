package javaCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import dbConnect.connect;
public class AdminView {

	// Method to enroll students
	public static void enrollStudent(Scanner ip){
		try{
			
			// Object to hold the results of the queries.
			ResultSet rs;
			
			// Object to hold the query.
			PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.enroll_new_student);
			
			System.out.println("\n**Enroll a student.**");
			System.out.print("Enter StudentId: ");
			int sid = ip.nextInt();
			System.out.print("Enter Student's First Name: ");
			String firstname = ip.next();
			System.out.print("Enter Student's Last Name: ");
			String lastname = ip.next();
			System.out.print("Enter Student's email address: ");
			String email = ip.next();
			
			String level_class, residency_class;
			while(true){
				System.out.println("Enter Student's level(Select an option from below): \n 1. Graduate \n 2. Undergraduate");
				System.out.print("Your choice: ");
				int choice = ip.nextInt();
				if(choice == 1){
					level_class = "Graduate";
					break;
				}
				else if(choice == 2){
					level_class = "Undergraduate";
					break;
				}
				else{
					System.out.println("Please select correct option.");
				}
				
			}
			
			while(true){
				System.out.println("Enter Student's Residency status(Select an option from below): \n 1. In-state \n 2. Out-state \n 3. International");
				System.out.print("Your choice: ");
				int choice = ip.nextInt();
				if(choice == 1){
					residency_class = "In-State";
					break;
				}
				else if(choice == 2){
					residency_class = "Out-State";
					break;
				}
				else if(choice == 3){
					residency_class = "International";
					break;
				}
				else{
					System.out.println("Please select correct option.");
				}
			}
			
			System.out.println("Enter Student's Date of Birth(YYYY-MM-DD): ");
			String dob = ip.next();
			
			pstmt.setInt(1, sid);
			pstmt.setString(2, Integer.toString(sid));
			pstmt.setString(3, firstname);
			pstmt.setString(4, lastname);
			pstmt.setString(5, email);
			pstmt.setString(6, level_class);
			pstmt.setString(7, residency_class);
			pstmt.setFloat(8, (float) 0.0);
			pstmt.setString(9, dob);
			
			rs = pstmt.executeQuery();
			//connect.close(pstmt);
			// Checking if the insertion of new student is successful or not.
			ResultSet rs1;
			PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.verify_login_student);
			pstmt1.setInt(1, sid);
			pstmt1.setString(2, Integer.toString(sid));
			rs1 = pstmt1.executeQuery();
			if(rs1.next()){
				System.out.println("New Student created Successfully");
				Login.admin_homepage(ip);
			}
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}	
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
