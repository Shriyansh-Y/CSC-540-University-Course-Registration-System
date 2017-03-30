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
			//ResultSet rs;
			
			// Object to hold the query.
			PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.enroll_new_student);
			
			System.out.println("\n**Enroll a student.**");
			System.out.print("Enter StudentId: ");
			int sid = ip.nextInt();
			System.out.print("Enter Student's username: ");
			String username = ip.next();
			System.out.print("Enter Student's First Name: ");
			String firstname = ip.next();
			System.out.print("Enter Student's Last Name: ");
			String lastname = ip.next();
			System.out.print("Enter Student's email address: ");
			String email = ip.next();
			System.out.print("Enter phone number for a student: ");
			String phone = ip.next();
			System.out.print("Enter Department for student: ");
			String dept = ip.next();
			
			
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
			
			System.out.print("Enter Student's Date of Birth(YYYY-MM-DD): ");
			String dob = ip.next();
			
			
			pstmt.setInt(1, sid);
			pstmt.setString(2, username);
			pstmt.setString(3, Integer.toString(sid));
			pstmt.setString(4, firstname);
			pstmt.setString(5, lastname);
			pstmt.setString(6, email);
			pstmt.setString(7, phone);
			pstmt.setString(8, dept);
			pstmt.setString(9, level_class);
			pstmt.setString(10, residency_class);
			pstmt.setFloat(11, (float) 0.0);
			pstmt.setString(12, dob);
			
			// Executing the insertion query.
			pstmt.executeQuery();
			connect.close(pstmt);
			// Checking if the insertion of new student is successful or not.
			ResultSet rs1;
			PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.verify_login_student);
			pstmt1.setString(1, username);
			pstmt1.setString(2, Integer.toString(sid));
			rs1 = pstmt1.executeQuery();
			if(rs1.next()){
				//System.out.println("\n~~New Student created Successfully~~\n");
				
				connect.close(pstmt1);
				
				// Updating the student's credit limits after successful enrollment.
				ResultSet r22;
				PreparedStatement pstmt3 = connect.getConnection().prepareStatement(Queries.insert_credit_limit);
				pstmt3.setInt(1, sid);
				pstmt3.setInt(2, 0);
				pstmt3.setInt(3, 0);
				pstmt3.setInt(4, 0);
				pstmt3.setInt(5, 0);
				pstmt3.executeQuery();
				//connect.close(pstmt3);
				
				PreparedStatement pstmt4 = connect.getConnection().prepareStatement(Queries.select_credits);
				pstmt4.setString(1, level_class);
				pstmt4.setString(2, residency_class);
				r22 = pstmt4.executeQuery();
				
				if(r22.next()){
					System.out.println("here" + r22.getString("min_credits"));
					System.out.println("here" + r22.getString("max_credits"));
					PreparedStatement pstmt5 = connect.getConnection().prepareStatement(Queries.update_credit_limit);
					pstmt5.setString(1, r22.getString("min_credits"));
					pstmt5.setString(2, r22.getString("max_credits"));
					pstmt5.setInt(3, sid);
					pstmt5.executeQuery();
					System.out.println("\n~~New Student created Successfully~~\n");
					connect.close(pstmt3);
					connect.close(pstmt4);
					connect.close(pstmt5);
					Login.admin_homepage(ip);
				}
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
		try{
			while(true){
				System.out.println("\n**View Student Details**");
				System.out.print("Enter the Student ID:");
				int sid = ip.nextInt();
				
				// Object to hold the results of the queries.
				ResultSet r;
				
				// Object to hold the query.
				PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.get_student_details);
				pstmt.setInt(1, sid);
				// Execute the query.
				r = pstmt.executeQuery();
				
				if(r.next()){
					System.out.println("First Name: " + r.getString("First_Name"));
					System.out.println("Last Name: " + r.getString("Last_Name"));
					System.out.println("Date of Birth: " + r.getDate("Dateofbirth"));
					System.out.println("Student's level: " + r.getString("level_class"));
					System.out.println("Student's Residency Status: " + r.getString("residency_class"));
					System.out.println("Student's GPA: " + r.getFloat("GPA"));
					System.out.println("Student's phone: " + r.getString("phone"));
					System.out.println("Student's email id: " + r.getString("email"));
					connect.close(pstmt);
					System.out.print("Press 0 to go back: ");
					int choice = ip.nextInt();
					if(choice == 0){
						Login.admin_homepage(ip);
					}
					else{
						System.out.println("Please enter 0 to go back.");
					}
				}
				else{
					System.out.println("Please enter correct Student Id.");
				}
			}
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
	}
	
	// Method to view admin profile
	public static void viewProfile(Scanner ip){
		while(true){
			System.out.println("**Your Profile**");
			System.out.println("First Name: " + AdminProfile.getInstance().getFirstname());
			System.out.println("Last Name: " + AdminProfile.getInstance().getLastname());
			System.out.println("Date of Birth: " + AdminProfile.getInstance().getDob());
			System.out.println("Employee id: " + AdminProfile.getInstance().getEid());
			System.out.print("Press 0 to go back: ");
		
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
		while(true){
			System.out.println("\n**View/Add Courses**");
			System.out.println("Select appropriate option:");
			System.out.println("0. Go to previous menu.");
			System.out.println("1. View Courses.");
			System.out.println("2. Add a Course.");
			System.out.print("Your choice: ");
			int choice = ip.nextInt();
			switch(choice){
			case 0:
				Login.admin_homepage(ip);
				break;
			case 1:
				Courses.viewCourse(ip);
				break;
			case 2:
				Courses.addCourse(ip);
				break;
			default:
				System.out.println("Please select a correct option.");
			}
		}
	}
	
	// Method to view/add Course offering
	public static void viewaddCourseOffering(Scanner ip){
		while(true){
			System.out.println("\n**View/Add Course Offering**");
			System.out.println("Select appropriate option:");
			System.out.println("0. Go to previous menu.");
			System.out.println("1. View Course Offering.");
			System.out.println("2. Add a Course Offering.");
			System.out.print("Your choice: ");
			int choice = ip.nextInt();
			switch(choice){
			case 0:
				Login.admin_homepage(ip);
				break;
			case 1:
				Courses.viewCourseOffering(ip);
				break;
			case 2:
				Courses.addCourseOffering(ip);
				break;
			default:
				System.out.println("Please select a correct option.");
			}
		}	
	}
	
}
