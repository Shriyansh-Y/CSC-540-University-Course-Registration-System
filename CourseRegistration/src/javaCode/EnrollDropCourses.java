package javaCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import dbConnect.*;

public class EnrollDropCourses {
	
	// Method to view/enroll course.
	public static void enrollCourses(Scanner ip){
		try{
			while(true){
			System.out.println("\n**Enroll into Courses**");
			System.out.println("List of Available courses: ");
			PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.view_available_courses);
			p1.setString(1, "SPRING");
			ResultSet r1 = p1.executeQuery();
			int i = 0;
			List<AvailableClasses> cdata = new ArrayList<AvailableClasses>();
			while(r1.next()){
				i += 1;
				AvailableClasses ac = new AvailableClasses();
				String cid = r1.getString("COURSE_ID");
				
				PreparedStatement p2 = connect.getConnection().prepareStatement(Queries.select_course_name);
				p2.setString(1, cid);
				ResultSet r2 = p2.executeQuery();
				
				if(r2.next()){
					ac.cname = r2.getString("COURSE_NAME");
				}
				ac.class_size = r1.getInt("CLASS_SIZE");
				ac.course_id = cid;
				ac.days = r1.getString("DAYS_OF_WEEK");
				ac.set_end_time(r1.getString("END_TIME").substring(2,7));
				ac.fname = r1.getString("FACULTY_NAME");
				ac.num_enrolled = r1.getInt("NUMBER_OF_ENROLLED");
				ac.sem = r1.getString("SEMESTER");
				ac.set_start_time(r1.getString("START_TIME").substring(2,7));
				ac.waitlist_size = r1.getInt("WAITLIST_SIZE");
				ac.waitlisted = r1.getInt("WAITLISTED");		
				cdata.add(ac);
			}
			if(i == 0){
				System.out.println("No available courses found.");
				StudentView.viewenrollCourses(ip);
			}
			System.out.println("Select the courses from below: ");
			System.out.println("Press 0 to go back.");

			System.out.println("Sr.No.".format("%-8s", "Sr.No.") + "Course Id".format("%-15s", "CourseId")+"Course Name".format("%-50s", "Course Name")+
					"Faculty".format("%-30s", "Faculty")+"Days".format("%-12s", "Days")+"Start time".format("%-15s", "Start time")+"End time");
			for(int k = 0; k < i; k++){
				String ks = Integer.toString(k + 1) + ".";
				System.out.println(ks.format("%-8s", ks)+cdata.get(k).course_id.format("%-15s", cdata.get(k).course_id)+
						cdata.get(k).cname.format("%-50s", cdata.get(k).cname)+cdata.get(k).fname.format("%-30s", cdata.get(k).fname)
						+cdata.get(k).days.format("%-12s", cdata.get(k).days)+cdata.get(k).start_time.format("%-15s", cdata.get(k).start_time)
						+cdata.get(k).end_time);
			}
			
			while(true){
				System.out.print("Your choice: ");
				int choice = ip.nextInt();
				if(choice == 0){
					StudentView.viewenrollCourses(ip);
				}
				if(choice > 0 && choice <= i){
					
					// Checking if any prerequisites required or not.
					boolean prereq = CheckEligibility.check_prerequisites(cdata.get(choice - 1));
					if(prereq == false){
						System.out.println("Prerequisites not met for this course. Please select another course.");
						EnrollDropCourses.enrollCourses(ip);
					}
					
					// Checking overall gpa requirement.
					boolean gpa_check = CheckEligibility.check_gpa(cdata.get(choice - 1));
					if(gpa_check == false){
						System.out.println("Overall GPA not met for this course. Please select another course.");
						EnrollDropCourses.enrollCourses(ip);
					}
					
					// Checking if credit limit is maintained.
//					boolean credit_limit = CheckEligibility.check_credit_limit(cdata.get(choice - 1));
//					if(credit_limit == false){
//						System.out.println("You are exceeding your courses maximum credit limit. Please drop a course to be eligible to enroll in other courses.");
//						dropCourse(2,ip);
//					}
//					else{
//						System.out.println("Credit limit is maintained.");
//					}
					
					// Checking if the course has any conflicts with other courses.
					boolean conflicts = CheckEligibility.check_schedule_conflicts(cdata.get(choice - 1));
					if(conflicts == false){
						System.out.println("The course timings are conflicting with other courses. Please select another course or drop the previously enrolled course.");
						EnrollDropCourses.enrollCourses(ip);
					}
					
					// Checking if special permission is required or not.
					boolean special_permission = CheckEligibility.special_permission(cdata.get(choice - 1));
					if(special_permission == false){
						System.out.println("This course requires special permission from the admin.");
						while(true){
							System.out.println("Do you want to send a special request: \n1. Yes\n2. No");
							System.out.print("Your choice: ");
							int ck = ip.nextInt();
							if(ck == 1){
								send_special_request(cdata.get(choice - 1));
								EnrollDropCourses.enrollCourses(ip);
							}
							else if(ck == 2){
								EnrollDropCourses.enrollCourses(ip);
							}
							else{
								System.out.println("Please Enter correct option.");
							}
						}
					}	
					break;
				}
				else
					System.out.println("Please Enter correct option.");
				}
			
			// Checking the prerequisites of the student if any.
			
			
			break;
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
	}
	
	// Method to drop a course.
	public static void dropCourse(int opt, Scanner ip){
		
		System.out.println("You are in drop course method.");
		if(opt == 1){
			StudentView.viewenrollCourses(ip);
		}
	}
	
	// Method to send a special permission.
	public static void send_special_request(AvailableClasses ac){
		System.out.println("Sending special request.");
		try{
			// Inserting special request.
			PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.insert_special_request);
			PreparedStatement p2 = connect.getConnection().prepareStatement(Queries.check_permission_entry);
			p2.setInt(1, StudentProfile.getInstance().getSid());
			p2.setString(2, ac.course_id);
			p2.setString(3, ac.fname);
			p2.setString(4, ac.sem);
			ResultSet r2 = p2.executeQuery();
			if(r2.next()){
				System.out.println("You have already sent a special request to enroll in this course.");
				return;
			}
			
			p1.setTimestamp(1, getCurrentTimeStamp());
			p1.setInt(2, StudentProfile.getInstance().getSid());
			p1.setString(3, ac.course_id);
			p1.setString(4, ac.fname);
			p1.setString(5, ac.sem);
			p1.setString(6, "Pending");
			p1.setString(7, "");
			p1.setString(8, "");
			p1.execute();
			System.out.println("Special Permission has been sent to the Admin.");
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
	}
	
	
	// Method to return a timestamp object.
	private static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());

	}
}