package javaCode;

public final class Queries {
	
	static final String verify_login_student = "Select * from Student where username = ? and psswd = ?";
	static final String verify_login_admin = "Select * from Admins where username = ? and psswd = ?";
	static final String enroll_new_student = "Insert into Student Values(?,?,?,?,?,?,?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD'))";
	static final String get_student_details = "Select * from Student Where Student_id = ?";
	static final String update_student_firstname = "Update Student Set First_Name = ? Where Student_id = ?";
	static final String update_student_lastname = "Update Student Set Last_Name = ? Where Student_id = ?";
	static final String update_student_phone = "Update Student Set phone = ? Where Student_id = ?";
	static final String update_student_email = "Update Student Set email = ? Where Student_id = ?";
	static final String verify_update_student_firstname = "Select * From Student Where First_Name = ? and Student_id = ?";
	static final String verify_update_student_lastname = "Select * From Student Where Last_Name = ? and Student_id = ?";
	static final String verify_update_student_phone = "Select * From Student Where phone = ? and Student_id = ?";
	static final String verify_update_student_email = "Select * From Student Where email = ? and Student_id = ?";	
}
