package javaCode;

public final class Queries {
	
	static final String verify_login_student = "Select * from Student where Student_id = ? and password = ?";
	static final String verify_login_admin = "Select * from Administrator where Employee_id = ? and password = ?";
}
