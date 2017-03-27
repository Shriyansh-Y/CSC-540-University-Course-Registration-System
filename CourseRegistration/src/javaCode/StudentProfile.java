package javaCode;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class StudentProfile {

	// Setting the session details
	private int sid;
	private String firstname;
	private String lastname;
	private String password;
	private Date dob;
	private int level_class;
	private int residency_class;
	private float gpa;
	private String email;
	
	private static StudentProfile studentProfile;
	
	public static StudentProfile getInstance(){
		if(studentProfile == null){
			studentProfile = new StudentProfile();
		}
		return studentProfile;
	}
	
	public static StudentProfile getNewInstance(){
		studentProfile = new StudentProfile();
		return studentProfile;
	}
	
	// Deleting the current instance when the student logs out.
	public static void deleteInstance(){
		studentProfile = null;
	}
	
	// Getting the current student's student id.
	public int getSid(){
		return sid;
	}
	
	// Setting the student id of the current logged in student.
	public void setSid(int sid){
		this.sid = sid;
	}
	
	// Getting the first name of student.
	public Date getDob(){
		return dob;
	}
	
	// Setting the date of birth of logged in student.
	public void setDob(Date dob){
		this.dob = dob;
	}
	
	// Getiing the first name of student.
	public String getFirstname(){
		return firstname;
	}
	
	// Setting the first name of student.
	public void setFirstname(String firstname){
		this.firstname = firstname;
	}
	
	// Getiing the first name of student.
	public String getLastname(){
		return lastname;
	}
		
	// Setting the first name of student.
	public void setLastname(String lastname){
		this.lastname = lastname;
	}
	
	// Getiing the level classification of student.
	public int getLevelclass(){
		return level_class;
	}
			
	// Setting the level classification of student.
	public void setLevelclass(int level_class){
		this.level_class = level_class;
	}
	
	// Getiing the password of student.
	public String getPassword(){
		return password;
	}
				
	// Setting the password of student.
	public void setPassword(String password){
		this.password = password;
	}	

	// Getiing the residency classification of student.
	public int getResidencyclass(){
		return residency_class;
	}
			
	// Setting the residency classification of student.
	public void setResidencyclass(int residency_class){
		this.residency_class = residency_class;
	}
	
	// Getiing the gpa of student.
	public float getGPA(){
		return gpa;
	}
			
	// Setting the gpa of student.
	public void setGPA(float gpa){
		this.gpa = gpa;
	}
	
	// Getiing the email of student.
	public String getEmail(){
		return email;
	}
				
	// Setting the email of student.
	public void setEmail(String email){
		this.email = email;
	}		
	
	// Setting up the profile.
	public void setupProfile(ResultSet r){
		try{
			setDob(r.getDate("Dateofbirth"));
			setSid(r.getInt("Student_id"));
			setFirstname(r.getString("First_Name"));
			setLastname(r.getString("Last_Name"));
			setPassword(r.getString("psswd"));
			setGPA(r.getFloat("GPA"));
			setLevelclass(r.getInt("level_class"));
			setResidencyclass(r.getInt("residency_class"));
			setEmail(r.getString("email"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
