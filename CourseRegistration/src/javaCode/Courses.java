package javaCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import dbConnect.connect;

public class Courses {
	
	public static void viewCourse(Scanner ip){
        try{
        	
            while(true){
                System.out.println("\n**View Course Details**");
                System.out.println("Enter 0 to go to main menu");
                System.out.print("Enter the Course ID:");
                String cid = ip.next();
           
                
                if(cid.equals(Integer.toString(0)))
                {
                	
                	Login.admin_homepage(ip);
             
                }
                else
                {
                ResultSet r;
                
                
                PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.view_course);
                pstmt.setString(1, cid);
                // Execute the query.
                r = pstmt.executeQuery();
                
                if(r.next()){
                    System.out.println("Course name: " + r.getString("course_name"));
                    System.out.println("Department name: " + r.getString("dept_name"));
                    System.out.println("Class level: " + r.getString("level_class"));
                    System.out.println("GPA requirement: " + r.getFloat("gpa_req"));
                    System.out.println("List of prerequisites: ");
                    String prereq=r.getString("pre_req_courses");
                    String[] prereqs=prereq.split(",");
                    for( int i=0;i<prereqs.length;i++)
                    {
                        System.out.printf("%d) %s\n",i+1,prereqs[i]);
                    }
                    System.out.println("Special approval required: " + r.getString("spcl_approval_req"));
                    System.out.println("Number of credits: " + r.getInt("credits"));
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
                    System.out.println("Please enter correct Course Id.");
                }
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
    
    // Method to add a course.
    public static void addCourse(Scanner ip){
        try{
            
            // Object to hold the results of the queries.
            ResultSet rs;
            
            // Object to hold the query.
            PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.add_new_course);
            
            System.out.println("\n*Add a course.**");
            System.out.print("Enter CourseID: ");
            String cid = ip.next();
            System.out.print("Enter Course name: ");
            String cname = ip.next();
            System.out.print("Enter Department Name: ");
            String dname = ip.next();
            
            String level_class;
            while(true){
                System.out.println("Enter Course level(Select an option from below): \n 1. Graduate \n 2. Undergraduate");
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
            
            
            
            System.out.print("Enter GPA requirement: ");
            Float gpaReq = ip.nextFloat();
            
            ip.nextLine();
            
            System.out.print("List of prerequisite courses (please enter required courses separated by comma): ");
            String preqreCourses = ip.nextLine();
            
            String specialPermission;
            while(true){
                System.out.println("State if special persmission is required (Select an option from below): \n 1. Yes \n 2. No");
                System.out.print("Your choice: ");
                int choice = ip.nextInt();
                if(choice == 1){
                    specialPermission = "Yes";
                    break;
                }
                else if(choice == 2){
                    specialPermission = "No";
                    break;
                }
                else{
                    System.out.println("Please select correct option.");
                }
                
            }
            
            
            System.out.print("Number of credits: ");
            int credits = ip.nextInt();
            
            
            pstmt.setString(1, cid);
            pstmt.setString(2, cname);
            pstmt.setString(3, dname);
            pstmt.setString(4, level_class);
            pstmt.setFloat(5, gpaReq);
            pstmt.setString(6, preqreCourses);
            pstmt.setString(7, specialPermission);
            pstmt.setInt(8, credits);
            
            
            // Executing the insertion query.
            rs = pstmt.executeQuery();

            // Checking if the insertion of new course is successful or not.
            ResultSet rs1;
            PreparedStatement pstmt1 = connect.getConnection().prepareStatement(Queries.view_course);
            pstmt1.setString(1, cid);
            rs1 = pstmt1.executeQuery();
            if(rs1.next()){
                System.out.println("\n~~New Course successfully added!~~\n");
                connect.close(pstmt);
                connect.close(pstmt1);
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

	// Method to view a Course Offering.
	public static void viewCourseOffering(Scanner ip){
//		System.out.println("\n**View a Course Offering**");
//		
//		try{
//			while(true){
//				System.out.print("Enter the Course ID:");
//				String cid = ip.next().toUpperCase();
//				
//				// Object to hold the results of the queries.
//				ResultSet r;
//				
//				// Object to hold the query.
//				PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.view);
//				pstmt.setInt(1, cid);
//				// Execute the query.
//				r = pstmt.executeQuery();
//				
//				if(r.next()){
//					System.out.println("First Name: " + r.getString("First_Name"));
//					System.out.println("Last Name: " + r.getString("Last_Name"));
//					System.out.println("Date of Birth: " + r.getDate("Dateofbirth"));
//					System.out.println("Student's level: " + r.getString("level_class"));
//					System.out.println("Student's Residency Status: " + r.getString("residency_class"));
//					System.out.println("Student's GPA: " + r.getFloat("GPA"));
//					System.out.println("Student's phone: " + r.getString("phone"));
//					System.out.println("Student's email id: " + r.getString("email"));
//					connect.close(pstmt);
//					System.out.print("Press 0 to go back: ");
//					int choice = ip.nextInt();
//					if(choice == 0){
//						Login.admin_homepage(ip);
//					}
//					else{
//						System.out.println("Please enter 0 to go back.");
//					}
//				}
//				else{
//					System.out.println("Please enter correct Student Id.");
//				}
//			}
//			
//		} catch (SQLException e){
//			e.printStackTrace();
//		}
//		catch (Exception e){
//			System.out.println("Invalid values entered. Please enter correct values.");
//			System.out.println(e.getMessage());
//		}
//		
	}
	
	// Method to add a Course Offering
	public static void addCourseOffering(Scanner ip){
		System.out.println("\n**Add a Course Offering**");
		
		try{
			
			ResultSet rs;
			PreparedStatement pstmt = connect.getConnection().prepareStatement(Queries.add_course_offerings);
			
			System.out.print("Enter Course Id: ");
			String cid = ip.next().toUpperCase();
			System.out.print("Enter Faculty Name for "+cid+" :");
			String firstName = ip.next().toUpperCase();
			String lastName = ip.next().toUpperCase();
			String facultyName = firstName+" "+lastName;
			System.out.println();
			System.out.print("Enter Semester for "+cid+" :");
			String semester;
			
			while(true){
				System.out.println("Enter Semester(Select an option from below): \n 1. FALL \n 2. SPRING \n 3. SUMMER");
				System.out.print("Your choice: ");
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
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}	
	}
	

}	
	
	
