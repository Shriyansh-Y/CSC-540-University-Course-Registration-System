package javaCode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import dbConnect.*;
import oracle.net.ns.AcceptPacket;

public class EnrollDropCourses {
	
	// Method to view/enroll course.
	public static void enrollCourses(Scanner ip){
		try{
			while(true){
			System.out.println("\n**Enroll into Courses**");
			System.out.println("List of Available courses: ");
			PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.view_available_courses);
			p1.setString(1, "SPRING");
			p1.setString(2,StudentProfile.getInstance().getLevelclass());
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
					
					
					// Check if you have already been enrolled in the course.
					PreparedStatement cp1 = connect.getConnection().prepareStatement(Queries.check_enrollment);
					cp1.setInt(1, StudentProfile.getInstance().getSid());
					cp1.setString(2, cdata.get(choice - 1).course_id);
					cp1.setString(3, cdata.get(choice - 1).sem);
					cp1.setString(4, cdata.get(choice - 1).fname);
					ResultSet cr1 = cp1.executeQuery();
					if(cr1.next()){
						System.out.println("You have already been enrolled in this course. Please select a different course.");
						EnrollDropCourses.enrollCourses(ip);
					}
					
					// Checking if you have already been waitlisted for the course.
					PreparedStatement cp2 = connect.getConnection().prepareStatement(Queries.check_waitlist);
					cp2.setInt(1, StudentProfile.getInstance().getSid());
					cp2.setString(2, cdata.get(choice - 1).course_id);
					cp2.setString(3, cdata.get(choice - 1).sem);
					cp2.setString(4, cdata.get(choice - 1).fname);
					ResultSet cr2 = cp2.executeQuery();
					if(cr2.next()){
						System.out.println("You have already been waitlisted in this course. Please select a different course.");
						EnrollDropCourses.enrollCourses(ip);
					}
										
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
					boolean credit_limit = CheckEligibility.check_credit_limit(cdata.get(choice - 1));
					
					
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
					
					// Checking the class size limit.
					boolean class_enroll = CheckEligibility.check_class_size(cdata.get(choice - 1));
					if(class_enroll == false){	
						// Enroll in wait list.
						while(true){
							System.out.println("The current class size is full. Do you wish to be placed on the waitlist:\n1. Yes\n2. No");
							System.out.print("Your choice: ");
							int n1 = ip.nextInt();
							if(n1 == 2){
								EnrollDropCourses.enrollCourses(ip);
							} else if(n1 == 1){
								
								if(credit_limit == false){
									while(true){
									System.out.println("You are exceeding your courses maximum credit limit.");
									System.out.println(" Please select a course to be dropped if you get enrolled in this course.");
									PreparedStatement ps1 = connect.getConnection().prepareStatement(Queries.get_cid);
									ps1.setInt(1, StudentProfile.getInstance().getSid());
									ResultSet rs1 = ps1.executeQuery();
									List<String> ar1 = new ArrayList<String>();
									System.out.println("Sr.No.".format("%-8s", "Sr.No.") + "Course Id".format("%-15s", "CourseId"));
									int t = 0;
									while(rs1.next()){
										ar1.add(rs1.getString("Course_Id"));
										String ks1 = Integer.toString(t + 1) + ".";
										System.out.println(ks1.format("%-8s", ks1)+ar1.get(t).format("%-15s", ar1.get(t)));
										t += 1;
									}
									System.out.println("Press 0 to go back.");
									System.out.print("Your choice: ");
									int c1 = ip.nextInt();
									if(c1 == 0)
									EnrollDropCourses.enrollCourses(ip);
									else if(c1 - 1 <= t){
										enroll_waitlist(StudentProfile.getInstance().getSid(), cdata.get(choice - 1).course_id, cdata.get(choice - 1).fname, 
												cdata.get(choice - 1).sem, cdata.get(choice - 1).waitlisted + 1, ar1.get(c1 - 1),ip);
									}else{
										System.out.println("Please select a correct option.");
									}
									}
								}
								else{
									enroll_waitlist(StudentProfile.getInstance().getSid(), cdata.get(choice - 1).course_id, cdata.get(choice - 1).fname, 
											cdata.get(choice - 1).sem, cdata.get(choice - 1).waitlisted + 1, "",ip);
								}
							}
							else{
								System.out.println("Please enter correct option.");
							}
						}	
					}
					else{
						enroll_class(StudentProfile.getInstance().getSid(), cdata.get(choice - 1).course_id, cdata.get(choice - 1).fname, 
								cdata.get(choice - 1).sem, "F",3,ip);
						EnrollDropCourses.enrollCourses(ip);
					}
				}
				else
					System.out.println("Please Enter correct option.");
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
	
	// Method to enroll in class.
	public static void enroll_class(int student_id, String course_id, String faculty, String sem, String l_grade, int credit, Scanner ip){
		try{
			PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.insert_in_enrollment);
			p1.setInt(1, student_id);
			p1.setString(2, course_id);
			p1.setString(3, faculty);
			p1.setString(4, sem);
			p1.setString(5, l_grade);
			p1.setInt(6, credit);
			p1.executeQuery();
			
			// Check if you have already been enrolled in the course.
			PreparedStatement cp1 = connect.getConnection().prepareStatement(Queries.check_enrollment);
			cp1.setInt(1, student_id);
			cp1.setString(2, course_id);
			cp1.setString(3, sem);
			cp1.setString(4, faculty);
			ResultSet cr1 = cp1.executeQuery();
			if(cr1.next()){
				System.out.println("~~Successfull Enrollment in the Course "+ course_id+"~~");
				EnrollDropCourses.enrollCourses(ip);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		catch (Exception e){
			System.out.println("Invalid values entered. Please enter correct values.");
			System.out.println(e.getMessage());
		}
	}
	
	public static void viewMyCourses(Scanner ip){
		
		try{
				PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.view_enrolled_courses);
				p1.setInt(1,StudentProfile.getInstance().getSid());
				ResultSet r1 = p1.executeQuery();
				int i = 0;
				List<EnrolledClasses> edata = new ArrayList<EnrolledClasses>();
				while(r1.next()){
					i += 1;
					EnrolledClasses ec = new EnrolledClasses();
					String cid = r1.getString("COURSE_ID");
					
					PreparedStatement p2 = connect.getConnection().prepareStatement(Queries.select_course_name);
					PreparedStatement p3 = connect.getConnection().prepareStatement(Queries.select_course_semester);

					p2.setString(1, cid);
					p3.setString(1, cid);
					p3.setInt(2, StudentProfile.getInstance().getSid());


					ResultSet r2 = p2.executeQuery();
					ResultSet r3 = p3.executeQuery();

					if(r2.next()){
						ec.course_name = r2.getString("COURSE_NAME");
					}
					if(r3.next()){
						ec.sem = r3.getString("SEMESTER");
					}
					ec.cid = cid;		
					edata.add(ec);
					
					
					connect.close(p2);
					connect.close(p3);

					
				}
				
				
				if(i == 0){
					System.out.println("No enrolled courses found.");
					StudentView.viewenrollCourses(ip);
				}
				System.out.println("My Courses : ");
				//System.out.println("Press 0 to go back.");

				System.out.println("Sr.No.".format("%-8s", "Sr.No.") + "Course Id".format("%-15s", "CourseId")+"Course Name".format("%-50s", "Course Name")+"Semester".format("%-30s", "Semester"));
				
				for(int k = 0; k < i; k++){
					String ks = Integer.toString(k + 1) + ".";
					System.out.println(ks.format("%-8s", ks) + edata.get(k).cid.format("%-15s", edata.get(k).cid) + edata.get(k).course_name.format("%-50s", edata.get(k).course_name) + edata.get(k).sem.format("%-30s", edata.get(k).sem));
				}
				
			
			connect.close(p1);
			StudentView.viewenrollCourses(ip);
		
	} catch (SQLException e){
		e.printStackTrace();
	}
	catch (Exception e){
		System.out.println("Invalid values entered. Please enter correct values.");
		System.out.println(e.getMessage());
	}
	}
	
	// Method to enroll in waitlist.
	public static void enroll_waitlist(int student_id, String course_id, String faculty, String sem, int wait_num, String dropc,Scanner ip){
		try{
			PreparedStatement p1 = connect.getConnection().prepareStatement(Queries.insert_in_waitlist);
			p1.setInt(1, student_id);
			p1.setString(2, course_id);
			p1.setString(3, faculty);
			p1.setString(4, sem);
			p1.setInt(5, wait_num);
			p1.setString(6, dropc);
			p1.executeQuery();
			
			// Check if you have already been enrolled in the course.
			PreparedStatement cp1 = connect.getConnection().prepareStatement(Queries.check_waitlist);
			cp1.setInt(1, student_id);
			cp1.setString(2, course_id);
			cp1.setString(3, sem);
			cp1.setString(4, faculty);
			ResultSet cr1 = cp1.executeQuery();
			if(cr1.next()){
				System.out.println("~~Successfully Waitlisted in the Course "+ course_id+"~~");
				EnrollDropCourses.enrollCourses(ip);
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