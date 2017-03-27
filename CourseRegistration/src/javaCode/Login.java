package javaCode;

import java.sql.*;
import java.util.Scanner;

import dbConnect.connect;

public class Login {
	
	// Method to verify login and to identify the user as admin or student.
	public static int login_verify(Scanner ip){
		do{
			try{
				// Object to hold the results of the queries.
				ResultSet rs1, rs2;
				
				// Object to hold the query.
				PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.verify_login_admin);
				PreparedStatement pstmt2 = connect.getConnection().prepareStatement(Queries.verify_login_student);
				
				System.out.println("Enter Username (Employee id / Student id): ");
				int user = ip.nextInt();
				System.out.println("Enter Password: ");
				String passwd = ip.next();
				
				// Setting the username and password parameters in the query.
				pstmt1.setInt(1, user);
				pstmt1.setString(2, passwd);
				pstmt2.setInt(1, user);
				pstmt2.setString(2, passwd);
				
				// Getting the results of the queries.
				rs1 = pstmt1.executeQuery();
				rs2 =  pstmt2.executeQuery();
				
				// Checking if the user is an admin or a student.
				// If the user is an admin.
				if(rs1.next()){
					System.out.println("Successful Admin Login!");
					// Setting up the session for admin.
					
					connect.close(pstmt1);
					connect.close(pstmt2);
					return 0;
				}
				// If the user is as student.
				else if(rs2.next()){
					System.out.println("Successful Student Login!");
					//Setting up the session for student.
					
					connect.close(pstmt1);
					connect.close(pstmt2);
					return 1;
				}
				// If the user has entered invalid credentials.
				else{
					System.out.println("Please enter correct values.");
				}
				
			} catch (SQLException e){
				e.printStackTrace();
			}
			catch (Exception e){
				System.out.println("Invalid values entered. Please enter correct values.");
				System.out.println(e.getMessage());
			}	
		}while(true);
	}
	
	
	// Method to display the initial menu options to the user.
	public static void startPage(Scanner ip){
		System.out.println("*****Course Registration System*****");

		while (true) {
			System.out.println("1. Admin/Student Login");
			System.out.println("2. Exit");
			System.out.print("Choice : ");
			System.out.println(ip);
			int choice = ip.nextInt();
			switch (choice){
			case 1:
				int status = login_verify(ip);
				if (status == 1){
					admin_homepage(ip);
				}
				else
					student_homepage(ip);
				break;
				
			// Closing the connection			
			case 2:
				connect.closeConnection();
				connect.closeStatement();
				//ip.close();
				System.exit(0);
			
			default:
				System.out.println("Pease enter correct choice.");
				
			}
		}
	}
	

	
	// Method to display options for an admin after successful login.
	public static void admin_homepage(Scanner ip){
		System.out.println("Hellllo admin.");
	}
	
	// Method to display options for a student after successfull login.
	public static void student_homepage(Scanner ip){
		System.out.println("Hellllo student.");
		
	}
}
