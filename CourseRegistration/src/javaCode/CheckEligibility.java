package javaCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbConnect.connect;

public class CheckEligibility {

	// Method for checking prerequisite courses.
	public static boolean check_prerequisites(AvailableClasses ac){
		
		try{
			// Fetching the prerequisites for the course.
			PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.find_prerequisites);
			p1.setString(1, ac.course_id);
			ResultSet r1 = p1.executeQuery();
			while(r1.next()){
				// Prerequisites found.
				PreparedStatement p2 = connect.getConnection().prepareStatement(Queries.check_prerequisites);
				p2.setInt(1, StudentProfile.getInstance().getSid());
				p2.setString(2, ac.course_id);
				ResultSet r2 = p2.executeQuery();
				if(!r2.next()){
					// Prerequisite not met.
					return false;
				}
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}	
		return true;
	}
	
	// Method for checking overall GPA requirement.
	public static boolean check_gpa(AvailableClasses ac){
		try{
			// Fetching the GPA requirement for this course.
			PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.view_course);
			p1.setString(1, ac.course_id);
			ResultSet r1 = p1.executeQuery();
			if(r1.next()){
				if(StudentProfile.getInstance().getGPA() < r1.getFloat("GPA_REQ")){
					return false;
				}		
			}	
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
		return true;
	}
	
	
	// Method to check if special permission is required or not.
	public static boolean special_permission(AvailableClasses ac){
		try{
			// Fetching current credit of the student.
			PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.view_course);
			p1.setString(1, ac.course_id);
			ResultSet r1 = p1.executeQuery();
			if(r1.next()){
				System.out.println(r1.getString("SPCL_APPROVAL_REQ"));
				if(r1.getString("SPCL_APPROVAL_REQ").equals("Yes")){
					return false;
				}
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
		return true;		
	}
	
	// Method to check conflicts in course timings.
	public static boolean check_schedule_conflicts(AvailableClasses ac){
		
		return true;
	}
	
	// Method to check credit limits.
	public static boolean check_credit_limit(AvailableClasses ac){
		try{
			// Fetching current credit of the student.
			PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.get_current_credit);
			p1.setInt(1, StudentProfile.getInstance().getSid());
			ResultSet r1 = p1.executeQuery();
			if(r1.next()){
				// Fetching the credits of the course.
				PreparedStatement p2 = connect.getConnection().prepareStatement(Queries.view_course);
				p2.setString(1, ac.course_id);
				ResultSet r2 = p2.executeQuery();
				if(r2.next()){
					if(r2.getString("VARIABLE_CREDIT").equals("Yes")){
						PreparedStatement p3 = connect.getConnection().prepareStatement(Queries.get_variable_credit);
						p3.setString(1, ac.course_id);
						ResultSet r3 = p3.executeQuery();
						if(r3.next()){
							if(((r1.getInt("CURRENT_CREDIT") + r3.getInt("MAX_CREDIT")) > r1.getInt("MAX_CREDIT"))){
								System.out.println("The credit sum is: "+(r1.getInt("CURRENT_CREDIT") + r3.getInt("MAX_CREDIT")));
								return false;
							}
						}
					}
					else if(r2.getString("VARIABLE_CREDIT").equals("No")){
						if((r1.getInt("CURRENT_CREDIT") + r2.getInt("CREDITS") > r1.getInt("MAX_CREDIT"))){
							return false;
						}		
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
		return true;
	}
}
