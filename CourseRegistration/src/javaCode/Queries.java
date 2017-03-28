package javaCode;

public final class Queries {
	
	static final String verify_login_student = "Select * from Student where username = ? and psswd = ?";
	static final String verify_login_admin = "Select * from Admins where username = ? and psswd = ?";
	static final String enroll_new_student = "Insert into Student Values(?,?,?,?,?,?,?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD'))";
	static final String get_student_details = "Select * from Student Where Student_id = ?";
	static final String add_course_offerings = "Insert into course_offering values(?,?,?,?,TO_DSINTERVAL(?),TO_DSINTERVAL(?),?,?,?)";
	static final String verify_course_offering = "Select * from course_offering where COURSE_ID = ? and FACULTY_NAME = ?";
	static final String view_course_offerings = "Select * from course_offering where COURSE_ID = ?";
}
