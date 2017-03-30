package javaCode;

import java.sql.*;
import java.util.Scanner;

import dbConnect.connect;

public class StudentView {
	
	// Method to view/edit student profile.
	public static void viewProfile(Scanner ip){
		while(true){
			System.out.println("\n**Your Profile**");
			System.out.println("First Name: " + StudentProfile.getInstance().getFirstname());
			System.out.println("Last Name: " + StudentProfile.getInstance().getLastname());
			System.out.println("Date of Birth: " + StudentProfile.getInstance().getDob());
			System.out.println("Email id: " + StudentProfile.getInstance().getEmail());
			System.out.println("Phone number: " + StudentProfile.getInstance().getPhone());
			System.out.println("Level: " + StudentProfile.getInstance().getLevelclass());
			System.out.println("Status: " + StudentProfile.getInstance().getResidencyclass());
			System.out.println("~~Select an option~~");
			System.out.print("0. Go back to previous menu.\n");
			System.out.println("1. Edit profile.");
			System.out.print("Your choice: ");
			int n = ip.nextInt();
			if(n == 0){
				Login.student_homepage(ip);
			}
			else if(n == 1){
				editProfile(ip);
			}
			else{
				System.out.println("Please enter 0 to go back.");
			}
		}
	}
	
	// Method to edit student profile.
	public static void editProfile(Scanner ip){
		while(true){
			System.out.println("\n**Edit Your Profile**");
			System.out.println("Select appropriate option.");
			System.out.println("0. Go back to previous menu.");
			System.out.println("1. Edit First Name.");
			System.out.println("2. Edit Last Name.");
			System.out.println("3. Edit Phone.");
			System.out.println("4. Edit Email id.");
			System.out.print("Your choice: ");
			int choice = ip.nextInt();
			switch(choice){
			case 0:
				viewProfile(ip);
				break;
			case 1:
				try{
					System.out.print("Enter new First Name: ");
					String firstname = ip.next();
					ResultSet r;
					PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.update_student_firstname);
					pstmt.setString(1, firstname);
					pstmt.setInt(2, StudentProfile.getInstance().getSid());
					pstmt.executeQuery();
					
					PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.verify_update_student_firstname);
					pstmt1.setString(1, firstname);
					pstmt1.setInt(2, StudentProfile.getInstance().getSid());
					r = pstmt1.executeQuery();
					if(r.next()){
						System.out.println("First name has been successfully changed.");
						// Updating the current session details to accommodate the changes made.
						StudentProfile.getInstance().setupProfile(r);
						connect.close(pstmt);
						connect.close(pstmt1);
						break;
					}
					else{
						System.out.println("Please enter a valid first name.");
					}
				} catch (SQLException e){
					e.printStackTrace();
				}
				catch (Exception e){
					System.out.println("Invalid values entered. Please enter correct values.");
					System.out.println(e.getMessage());
				}
				
			case 2:
				try{
					System.out.print("Enter new Last Name: ");
					String lastname = ip.next();
					ResultSet r;
					PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.update_student_lastname);
					pstmt.setString(1, lastname);
					pstmt.setInt(2, StudentProfile.getInstance().getSid());
					pstmt.executeQuery();
					
					PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.verify_update_student_lastname);
					pstmt1.setString(1, lastname);
					pstmt1.setInt(2, StudentProfile.getInstance().getSid());
					r = pstmt1.executeQuery();
					if(r.next()){
						System.out.println("Last name has been successfully changed.");
						// Updating the current session details to accommodate the changes made.
						StudentProfile.getInstance().setupProfile(r);
						connect.close(pstmt);
						connect.close(pstmt1);
						break;
					}
					else{
						System.out.println("Please enter a valid last name.");
					}
				} catch (SQLException e){
					e.printStackTrace();
				}
				catch (Exception e){
					System.out.println("Invalid values entered. Please enter correct values.");
					System.out.println(e.getMessage());
				}
			
			case 3:
				try{
					System.out.print("Enter new Phone Number: ");
					String phone = ip.next();
					ResultSet r;
					PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.update_student_phone);
					pstmt.setString(1, phone);
					pstmt.setInt(2, StudentProfile.getInstance().getSid());
					pstmt.executeQuery();
					
					PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.verify_update_student_phone);
					pstmt1.setString(1, phone);
					pstmt1.setInt(2, StudentProfile.getInstance().getSid());
					r = pstmt1.executeQuery();
					if(r.next()){
						System.out.println("Phone number has been successfully changed.");
						// Updating the current session details to accommodate the changes made.
						StudentProfile.getInstance().setupProfile(r);
						connect.close(pstmt);
						connect.close(pstmt1);
						break;
					}
					else{
						System.out.println("Please enter a valid phone number.");
						System.out.println();
					}
				} catch (SQLException e){
					e.printStackTrace();
				}
				catch (Exception e){
					System.out.println("Invalid values entered. Please enter correct values.");
					System.out.println(e.getMessage());
				}
			
			case 4:
				try{
					System.out.print("Enter new Email: ");
					String email = ip.next();
					ResultSet r;
					PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.update_student_email);
					pstmt.setString(1, email);
					pstmt.setInt(2, StudentProfile.getInstance().getSid());
					pstmt.executeQuery();
					
					PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.verify_update_student_email);
					pstmt1.setString(1, email);
					pstmt1.setInt(2, StudentProfile.getInstance().getSid());
					r = pstmt1.executeQuery();
					if(r.next()){
						System.out.println("Email has been successfully changed.");
						// Updating the current session details to accommodate the changes made.
						StudentProfile.getInstance().setupProfile(r);
						connect.close(pstmt);
						connect.close(pstmt1);
						break;
					}
					else{
						System.out.println("Please enter a valid email.");
					}
				} catch (SQLException e){
					e.printStackTrace();
				}
				catch (Exception e){
					System.out.println("Invalid values entered. Please enter correct values.");
					System.out.println(e.getMessage());
				}
				
			default:
				System.out.println("Please select correct option.");
			}
		}
	}
	
	// Method to view/enroll courses.
	public static void viewenrollCourses(Scanner ip){
		
	}
	
	// Method to view Pending courses
	public static void viewPendingCourses(Scanner ip){
		while(true){
			System.out.println("\n**View Courses and Status**");
			System.out.print("Press 0 to go back to previous Menu: ");
			int choice = ip.nextInt();
			if(choice == 0){
				Login.student_homepage(ip);
			}
			else{
				System.out.println("Please select correct option.");
			}
		}
	}
	
	// Method to display the grades of student.
	public static void viewGrades(Scanner ip){
		while(true){
			System.out.println("\n**View Grades/GPA**");
			System.out.println("Select an appropriate option: ");
			System.out.println("0. Go back to previous Menu.");
			System.out.println("1. Display Letter Grades.");
			System.out.println("2. Display GPA");
			System.out.print("Your Choice: ");
			int choice = ip.nextInt();
			if(choice == 0){
				Login.student_homepage(ip);
			}
			else if(choice == 1){
				
			}
			else if(choice == 2){
				
			}
			else{
				System.out.println("Please select a corret option");
			}
		}
			
		
	}
	
	// Method to view/pay Bills.
	public static void viewpayBills(Scanner ip){
		while(true){
			System.out.println("\n**View/Pay Letter Bills**");
			System.out.println("1. Display Student's Balance.");
			System.out.println("2. Enter Amount");
			System.out.print("Your Choice: ");
			int choice = ip.nextInt();
			if(choice == 0){
				Login.student_homepage(ip);
			}
			else if(choice == 1){
				//Displaying the student bill
			}
			else if(choice == 2){
				//method involing bill module
			}
			else{
				System.out.println("Please select a correct option");
			}
		}
	}
}
