package javaCode;

public final class Queries {
	
	static final String verify_login_student = "Select * from Student where username = ? and psswd = ?";
	static final String verify_login_admin = "Select * from Admins where username = ? and psswd = ?";
	static final String enroll_new_student = "Insert into Student Values(?,?,?,?,?,?,?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD'))";
	static final String get_student_details = "Select * from Student Where Student_id = ?";
	static final String add_course_offerings = "Insert into course_offering values(?,?,?,?,TO_DSINTERVAL(?),TO_DSINTERVAL(?),?,?,?)";
	static final String verify_course_offering = "Select * from course_offering where COURSE_ID = ? and FACULTY_NAME = ?";
	
	static final String view_course_offerings = "Select * from course_offering where COURSE_ID = ?";
	
	static final String update_student_firstname = "Update Student Set First_Name = ? Where Student_id = ?";
	static final String update_student_lastname = "Update Student Set Last_Name = ? Where Student_id = ?";
	static final String update_student_phone = "Update Student Set phone = ? Where Student_id = ?";
	static final String update_student_email = "Update Student Set email = ? Where Student_id = ?";
	static final String verify_update_student_firstname = "Select * From Student Where First_Name = ? and Student_id = ?";
	static final String verify_update_student_lastname = "Select * From Student Where Last_Name = ? and Student_id = ?";
	static final String verify_update_student_phone = "Select * From Student Where phone = ? and Student_id = ?";
	static final String verify_update_student_email = "Select * From Student Where email = ? and Student_id = ?";
	static final String verify_course_for_course_offering = "Select * from Courses where COURSE_ID = ?";
	static final String verify_update_student_email = "Select * From Student Where email = ? and Student_id = ?";	
	static final String add_new_course = "Insert into courses values(?,?,?,?,?,?,?,?)";
	static final String view_course = "Select * from courses where course_id = ?";
	static final String insert_credit_limit = "Insert into STUDENTCREDIT Values(?,?,?,?)";
	static final String update_credit_limit = "Update STUDENTCREDIT "
			+ "Set STUDENTCREDIT.min_credit = ?, STUDENTCREDIT.max_credit = ? "
			+ "where STUDENTCREDIT.Student_id = ?";
	static final String select_credits = "Select min_credits, max_credits from Creditmap where level_class = ? and residency_class = ?";
}
