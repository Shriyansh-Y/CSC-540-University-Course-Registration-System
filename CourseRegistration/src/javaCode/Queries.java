package javaCode;

public final class Queries {
	
	static final String verify_login_student = "Select * from Student where username = ? and psswd = ?";
	static final String verify_login_admin = "Select * from Admins where username = ? and psswd = ?";
	static final String enroll_new_student = "Insert into Student Values(?,?,?,?,?,?,?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD'))";
	static final String get_student_details = "Select * from Student Where Student_id = ?";
	static final String add_course_offerings = "Insert into course_offering values(?,?,?,?,TO_DSINTERVAL(?),TO_DSINTERVAL(?),?,?,?,?)";
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
	static final String add_new_course = "Insert into courses values(?,?,?,?,?,?,?,?,?)";
	static final String view_course = "Select * from courses where course_id = ?";
	static final String insert_credit_limit = "Insert into STUDENTCREDIT Values(?,?,?,?,?)";
	static final String update_credit_limit = "Update STUDENTCREDIT "
			+ "Set STUDENTCREDIT.min_credit = ?, STUDENTCREDIT.max_credit = ? "
			+ "where STUDENTCREDIT.Student_id = ?";
	static final String select_credits = "Select min_credits, max_credits from Creditmap where level_class = ? and residency_class = ?";
	static final String add_new_prereq= "Insert into prerequisite values(?,?,?,?)";
	static final String get_grade = "Select number_grade From grademap Where letter_grade = ?";    
	static final String get_prereqs = "Select prerequisite_id From prerequisite Where course_id = ?";
	static final String insert_variable_credit = "Insert into VARIABLE_CEDIT_COURSES Values(?,?,?)";
	static final String insert_default_bill = "Insert into STUDENTBILL(Student_id) VALUES(?)";
	static final String view_available_courses = "SELECT * FROM COURSE_OFFERING WHERE SEMESTER = ? and (NUMBER_OF_ENROLLED < CLASS_SIZE or WAITLISTED < WAITLIST_SIZE)";
	static final String select_course_name = "Select COURSE_NAME FROM COURSES WHERE COURSE_ID = ?";
	static final String find_prerequisites = "Select * From Prerequisite where COURSE_ID = ?";
	static final String check_prerequisites = "Select * from Enrollment where Semester = 'FALL' and Student_id = ? and Course_id = ?";
	static final String get_current_credit = "Select * from STUDENTCREDIT WHERE Student_id = ?";
	static final String view_enrolled_courses = "Select * from Enrollment where Student_id = ?";
	static final String select_course_semester = "Select SEMESTER from enrollment where COURSE_ID = ? and STUDENT_ID = ?";
	static final String view_grade = "Select COURSE_ID,LETTER_GRADE,SEMESTER from ENROLLMENT where STUDENT_ID = ? AND LETTER_GRADE <> 'F' ";
	static final String view_gpa = "Select GPA from STUDENT where STUDENT_ID = ? ";
	static final String view_total_bill = "Select TOTAL_AMOUNT-AMOUNT_PAID from STUDENTBILL where STUDENT_ID = ? ";
	static final String pay_bill = "Update STUDENTBILL Set AMOUNT_PAID = AMOUNT_PAID + ? Where STUDENT_ID = ? ";
	//static final String select_course_semester = "Select SEMESTER from course_offering where COURSE_ID = ?";
	static final String get_variable_credit = "Select * FROM VARIABLE_CEDIT_COURSES WHERE COURSE_ID = ?";
	static final String insert_special_request = "Insert into SPECIAL_PERMISSION VALUES(?,?,?,?,?,?,?,?)";
	static final String check_permission_entry = "Select * FROM SPECIAL_PERMISSION WHERE student_id = ? and course_id = ? and faculty = ? and semester = ?";
	static final String insert_in_enrollment = "Insert into enrollment values(?,?,?,?,?,?)";
	static final String check_enrollment = "Select * from enrollment where student_id = ? and course_id = ? and semester = ? and faculty = ?";
	static final String check_waitlist = "Select * from waitlist where student_id = ? and course_id = ? and semester = ? and faculty = ?";
	static final String insert_in_waitlist = "Insert into waitlist values(?,?,?,?,?,?)";
	static final String check_schedule_enrolled = "SELECT COURSE_ID FROM ENROLLMENT WHERE STUDENT_ID = ? and semester = 'SPRING' UNION ALL"
			+ "SELECT COURSE_ID FROM WAITLIST WHERE STUDENT_ID = ? and semester = 'SPRING'";
	static final String get_cid = "select * from ENROLLMENT WHERE student_id = ? and semester = 'SPRING'";
	static final String get_enrolled = "Select * from ENROLLMENT WHERE STUDENT_ID = ? and SEMESTER = 'SPRING' and LETTER_GRADE = 'F'";
	static final String get_waitlisted = "Select * from WAITLIST WHERE STUDENT_ID = ? and SEMESTER = 'SPRING'";
	static final String drop_waitlist = "DELETE FROM WAITLIST WHERE STUDENT_ID = ? and COURSE_ID = ? and FACULTY = ? and SEMESTER = 'SPRING'";
	static final String drop_enrolled = "";
	static final String update_waitlist_number = "UPDATE WAITLIST SET WAITLIST_NUMBER = WAITLIST_NUMBER - 1 WHERE WAITLIST_NUMBER > ? and COURSE_ID = ?";
	static final String view_enrolled_courses_currsem = "Select * from Enrollment where Student_id = ? and semester = 'SPRING'";
	static final String update_grade_enrollment="update enrollment set letter_grade=? where student_id=? and course_id=? ";
	static final String update_grade_credit_enrollment="update enrollment set letter_grade=?, credit=? where student_id=? and course_id=? ";
	static final String proc_call_update_grade="{call calgpa (?)}";

}
