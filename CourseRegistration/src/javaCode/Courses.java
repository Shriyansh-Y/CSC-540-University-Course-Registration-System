package javaCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import dbConnect.connect;

public class Courses {
	
	// Method to view a Course.
	public static void viewCourse(Scanner ip){
		System.out.println("\n**View a Course**");
		
	}
	
	// Method to add a course.
	public static void addCourse(Scanner ip){
		System.out.println("\n**Add a Course**");
	}
	

	// Method to view a Course Offering.
	public static void viewCourseOffering(Scanner ip){
		System.out.println("\n**View a Course Offering**");
		
		try{
				System.out.println();
				ResultSet r;
				PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.view_course_offerings);

				System.out.print("Enter the Course ID:");
				String cid = ip.next().toUpperCase();
				
				pstmt.setString(1, cid);
				// Execute the query.
				r = pstmt.executeQuery();
				
				if (!r.next()) {
					System.out.println("No Course Offerings found.");
				} else {
					
				do{
					System.out.println("Course Id: " + r.getString("COURSE_ID"));
					System.out.println("Faculty Name: " + r.getString("FACULTY_NAME"));
					System.out.println("Semester: " + r.getString("SEMESTER"));
					System.out.println("Days: " + r.getString("DAYS_OF_WEEK"));
					System.out.println("Start Time: " + r.getString("START_TIME"));
       				System.out.println("End Time: " + r.getString("END_TIME"));
					System.out.println("Class Size: " + r.getInt("CLASS_SIZE"));
					System.out.println("Number of students enrolled: " + r.getInt("NUMBER_OF_ENROLLED"));
					System.out.println("Waitlist Size: " + r.getInt("WAITLIST_SIZE"));

					System.out.println();
					System.out.println();
				}while(r.next());
			  }
				connect.close(pstmt);
				AdminView.viewaddCourseOffering(ip);
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
		
	}
	
	// Method to add a Course Offering
	public static void addCourseOffering(Scanner ip){
		System.out.println("\n**Add a Course Offering**");
		
		try{
			
			ResultSet rs;
			PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.add_course_offerings);
			
			String cid;
			
			ResultSet rs2;
			PreparedStatement pstmt2 = connect.getConnection().prepareStatement(Queries.verify_course_for_course_offering);
			System.out.print("Enter Course Id: ");
			cid = ip.next().toUpperCase();
			pstmt2.setString(1,cid);
			rs2 = pstmt2.executeQuery();
			if (!rs2.next()){
				System.out.println("\n~~Course ID entered is not valid~~\n");
				connect.close(pstmt2);
				addCourseOffering(ip);
			}
			else {
			connect.close(pstmt2);
			}
			System.out.print("Enter Faculty Name for "+cid+" :");
//			String firstName = ip.nextLine().toUpperCase();
//			String lastName = ip.next().toUpperCase();
//			String facultyName = firstName+" "+lastName;
//			System.out.println();
			ip.nextLine();
			
			String facultyName = ip.nextLine().toUpperCase();
			System.out.print("Enter Semester for "+cid+" :");
			String semester;
			
			while(true){
				System.out.println("Enter Semester(Select 1/2/3): \n 1. FALL \n 2. SPRING \n 3. SUMMER");
				System.out.print("Your choice no.: ");
				int choice = ip.nextInt();
				if(choice == 1){
					semester = "FALL";
					break;
				}
				else if(choice == 2){
					semester = "SPRING";
					break;
				}
				else if(choice == 3){
					semester = "SUMMER";
					break;
				}
				else{
					System.out.println("Please select correct option.");
				}
				
			}
			
			System.out.print("Enter Days of week for this course comma separated (MON,TUE,WED,THU,FRI) for "+cid+" :");
			String days = ip.next().toUpperCase();
			System.out.print("Enter course offering's start time (HH:MM:SS) for "+cid+" :");
			String start = ip.next();
			String start_time = "0 "+start;
			System.out.print("Enter course offering's end time (HH:MM:SS) for "+cid+" :");
			String end = ip.next();
			String end_time = "0 "+end;
			System.out.print("Enter class size for "+cid+" :");
			int class_size = ip.nextInt();
			System.out.print("Enter Number of students enrolled for "+cid+" :");
			int num_enrolled = ip.nextInt();
			System.out.print("Enter waitlist size for "+cid+" :");
			int waitlist_size = ip.nextInt();
			System.out.println("Confirm Adding the Course Offering for "+cid+" : \n 1: YES \n 2: No");
			int choice = ip.nextInt();
			if (choice == 2){
				AdminView.viewaddCourseOffering(ip);
			}
			else if (choice == 1) {
			
			pstmt.setString(1, cid);
			pstmt.setString(2, facultyName);
			pstmt.setString(3, semester);
			pstmt.setString(4, days);
			pstmt.setString(5, start_time);
			pstmt.setString(6, end_time);
			pstmt.setInt(7, class_size);
			pstmt.setInt(8, num_enrolled);
			pstmt.setInt(9, waitlist_size);
			
			// Executing the insertion query.
			rs = pstmt.executeQuery();
			}
			// Checking if the insertion of new course offering is successful or not.
			ResultSet rs1;
			PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.verify_course_offering);
			pstmt1.setString(1, cid);
			pstmt1.setString(2, facultyName);
			rs1 = pstmt1.executeQuery();
			if(rs1.next()){
				System.out.println("\n~~New Course Offering created Successfully~~\n");
				connect.close(pstmt);
				connect.close(pstmt1);
				AdminView.viewaddCourseOffering(ip);
			}
			else {System.out.println("Error");}
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}	
	}
	

}

	
	
