package javaCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

			System.out.println("Sr.No.".format("%-8s", "Sr.No.") + "Course Id".format("%-15s", "CourseId")+"Course Name".format("%-50s", "Course Name")+
					"Faculty".format("%-30s", "Faculty")+"Days".format("%-12s", "Days")+"Start time".format("%-15s", "Start time")+"End time");
			for(int k = 0; k < i; k++){
				String ks = Integer.toString(k + 1) + ".";
				System.out.println(ks.format("%-8s", ks)+cdata.get(k).course_id.format("%-15s", cdata.get(k).course_id)+
						cdata.get(k).cname.format("%-50s", cdata.get(k).cname)+cdata.get(k).fname.format("%-30s", cdata.get(k).fname)
						+cdata.get(k).days.format("%-12s", cdata.get(k).days)+cdata.get(k).start_time.format("%-15s", cdata.get(k).start_time)
						+cdata.get(k).end_time);
			}
			System.out.print("Your choice: ");
			int choice = ip.nextInt();
			System.out.println(cdata.get(choice - 1).cname);
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
	public static void dropCourse(Scanner ip){
		
	}
}