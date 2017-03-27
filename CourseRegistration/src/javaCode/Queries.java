package javaCode;

public final class Queries {
	
	static final String verify_login_student = "Select * from Student where Student_id = ? and psswd = ?";
	static final String verify_login_admin = "Select * from Admins where Employee_id = ? and psswd = ?";
	static final String enroll_new_student = "Insert into Student Values(?,?,?,?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD'))";

}
