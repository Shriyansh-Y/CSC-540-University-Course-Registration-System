package javaCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
		
		return true;
	}
	
	// Method to check conflicts in course timings.
	
	
	
	public static boolean check_schedule_conflicts(AvailableClasses ac){
		try{
			// Fetching current courses of the student.
			PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.check_schedule);
			p1.setInt(1, StudentProfile.getInstance().getSid());
			ResultSet r1 = p1.executeQuery();
			
			PreparedStatement p2 = connect.getConnection().prepareStatement(Queries.view_course_offerings);
			//System.out.printf("jisme enroll karne ka hai %s\n",ac.course_id);
			p2.setString(1, ac.course_id);
			ResultSet r2 = p2.executeQuery();
			if(r2.next())
			{
				String days=r2.getString("Days_of_week");
				List<String> daysarray=Arrays.asList(days.split(","));
				
				for(String d:daysarray)
					System.out.println(d);
			
			
			// ECST - Start time of Course you want to enroll
			// ECET - End time of course you want to enroll
			Date ECST=gettime(r2.getString("start_time"));
			Date ECET=gettime(r2.getString("end_time"));
			//System.out.println(ECST);
			//System.out.println(ECET);

			//System.out.println("____________________________________");
			while(r1.next())
			{
				String courseInHand=r1.getString("Course_id");
				//System.out.printf("jo enrolled hai %s\n",courseInHand);

				PreparedStatement p3 = connect.getConnection().prepareStatement(Queries.view_course_offerings);
				p3.setString(1, courseInHand);
				ResultSet rcourse = p3.executeQuery();
				if(rcourse.next())
				{
				//System.out.printf("jo enrolled hai %s\n",courseInHand);
				String courseInHandDays=rcourse.getString("Days_of_week");
				String[] courseInHandDaysArray=courseInHandDays.split(",");
				
				// CHST - Course in hand Start Time
				// CHET - Course in hand End Time
				Date CHST=gettime(rcourse.getString("start_time"));
				Date CHET=gettime(rcourse.getString("end_time"));
				
				//System.out.println(CHST);
				//System.out.println(CHET);

				//System.out.println("***********************************");
				for(String s:courseInHandDaysArray)
				{
					if(daysarray.contains(s))
					{   
						if(CHST.compareTo(ECST)== 0 || (ECST.compareTo(CHST) > 0 && ECST.compareTo(CHET) < 0) || (ECET.compareTo(CHST) > 0 && ECET.compareTo(CHET) < 0) || (CHST.compareTo(ECST) > 0 && CHET.compareTo(ECET) < 0))
						{                            
							//System.out.println("Time is Clashing");
							return false;
							
						}
						
					}
				}
				}
			}
			}	
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values..--------------------------------------------");
			System.out.println(e.getMessage());
		}
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
					if((r1.getInt("CURRENT_CREDIT") + r2.getInt("CREDITS") > r1.getInt("MAX_CREDIT"))){
						return false;
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
	
	
	public static Date gettime(String s)
	{
		//System.out.println("gettime function");
		System.out.println(s);

		s=s.substring(2);
		String s12, s3, s4,sfinal;
		s12 = s.substring(0,2);
		s3 = s.substring(3,4);
		s4 = s.substring(4);
		if(s4.equals(":"))
			 s4 = "0";
		
		sfinal = s12+":"+s3+s4;
		SimpleDateFormat f=new SimpleDateFormat("hh:mm");
		Date date=null;
		try {
			date = f.parse(sfinal);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(date);
		
		return date;
		
	}
	
	
	
	
}
