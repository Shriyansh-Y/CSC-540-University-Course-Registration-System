package javaCode;

import java.awt.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import dbConnect.connect;

public class DropCourse {
	
	public static void drop_course(Scanner ip){
		try{
			System.out.println("\n** Drop a Course **");
			PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.get_enrolled);
			p1.setInt(1, StudentProfile.getInstance().getSid());
			ResultSet r1 = p1.executeQuery();
			ArrayList<AvailableClasses> cdata = new ArrayList<AvailableClasses>();
			while(r1.next()){
				AvailableClasses ac = new AvailableClasses();
				
				PreparedStatement p2 = connect.getConnection().prepareStatement(Queries.select_course_name);
				p2.setString(1, r1.getString("COURSE_ID"));
				ResultSet r2 = p2.executeQuery();	
				if(r2.next()){
					ac.cname = r2.getString("COURSE_NAME");
				}
				ac.course_id = r1.getString("COURSE_ID");
				ac.sem = r1.getString("SEMESTER");
				ac.fname = r1.getString("FACULTY");
				cdata.add(ac);
			}
			System.out.println("Select the courses from below: ");
			System.out.println("Press 0 to go back.");
			System.out.println("List of Enrolled courses: ");
			
			System.out.println("Sr.No.".format("%-8s", "Sr.No.") + "Course Id".format("%-15s", "CourseId")+"Course Name".format("%-50s", "Course Name")+
					"Faculty".format("%-30s", "Faculty"));
			
			for(int i = 0; i < cdata.size(); i++){
				String is = Integer.toString(i + 1) + ".";
				System.out.println(is.format("%-8s", is)+cdata.get(i).course_id.format("%-15s", cdata.get(i).course_id)+
						cdata.get(i).cname.format("%-50s", cdata.get(i).cname)+cdata.get(i).fname.format("%-30s", cdata.get(i).fname));
				
			}
			
			PreparedStatement p3 = connect.getConnection().prepareStatement(Queries.get_waitlisted);
			p3.setInt(1, StudentProfile.getInstance().getSid());
			ResultSet r3 = p3.executeQuery();
			ArrayList<AvailableClasses> cdata1 = new ArrayList<AvailableClasses>();
			while(r3.next()){
				AvailableClasses ac = new AvailableClasses();
				
				PreparedStatement p4 = connect.getConnection().prepareStatement(Queries.select_course_name);
				p4.setString(1, r3.getString("COURSE_ID"));
				ResultSet r4 = p4.executeQuery();	
				if(r4.next()){
					ac.cname = r4.getString("COURSE_NAME");
				}
				ac.course_id = r3.getString("COURSE_ID");
				ac.sem = r3.getString("SEMESTER");
				ac.fname = r3.getString("FACULTY");
				ac.wait_number = r3.getInt("WAITLIST_NUMBER");
				ac.dropc = r3.getString("DROP_COURSE");
				cdata1.add(ac);
			}
			if(cdata1.size() > 0)
			System.out.println("List of Waitlisted course: ");
			for(int t = 0; t < cdata1.size(); t++){
				String ts = Integer.toString(t + cdata.size() + 1) + ".";
				System.out.println(ts.format("%-8s", ts)+cdata1.get(t).course_id.format("%-15s", cdata1.get(t).course_id)+
						cdata1.get(t).cname.format("%-50s", cdata1.get(t).cname)+cdata1.get(t).fname.format("%-30s", cdata1.get(t).fname));	
			}
			
			
			while(true){
				System.out.print("Your choice: ");
				int choice = ip.nextInt();
				if(choice == 0){
					StudentView.viewenrollCourses(ip);
				}
				else if(choice < 0 && choice > cdata.size()+cdata1.size()){
					System.out.println("Select correct option.");
				}
				else if(choice <= cdata.size()){
					drop_enrolled(cdata.get(choice - 1));
				}
				else if(choice > cdata.size() && choice <= cdata.size()+cdata1.size()){
					drop_waitlisted(cdata1.get(choice - 1 - cdata.size()));
				}
			}
		}  catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
		
	}
	
	// Method to drop the enrolled course.
	public static void drop_enrolled(AvailableClasses ac){
		try{
			PreparedStatement pe1 = connect.getConnection().prepareStatement(Queries.drop_enrolled);
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
	}
	
	// Method to drop the waitlisted course.
	public static void drop_waitlisted(AvailableClasses ac){
		
		try{
			PreparedStatement pw1 = connect.getConnection().prepareStatement(Queries.drop_waitlist);
			pw1.setInt(1, StudentProfile.getInstance().getSid());
			pw1.setString(2, ac.course_id);
			pw1.setString(3, ac.fname);
			
			pw1.executeUpdate();
			
			PreparedStatement pw2 = connect.getConnection().prepareStatement(Queries.update_waitlist_number);
			pw2.setInt(1, ac.wait_number);
			pw2.setString(2, ac.course_id);
			pw2.executeUpdate();
			
			System.out.println("Waitlisted course: "+ac.course_id+" "+ac.cname+" dropped Successfully!");
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
	}
}
