/******************* DROPPING TABLE TO REMOVE EXISTING TABLES IF ANY *******************/
DROP TABLE Student;
DROP TABLE Admins;
DROP TABLE CREDITMAP;
DROP TABLE STUDENTCREDIT;
DROP TABLE GRADEMAP;
DROP TABLE PREREQUISITE;
DROP TABLE COURSE_OFFERING;
DROP TABLE COURSES;
DROP TABLE CREDITMAP;
DROP TABLE ENROLLMENT;
DROP TABLE SPECIAL_PERMISSION;
DROP TABLE WAITLIST;
DROP TABLE STUDENTBILL;
DROP TABLE VARIABLE_CEDIT_COURSES;
DROP TABLE DEADLINE_ENFORCED;

/************************************ TABLE CREATION ************************************/
CREATE TABLE Admins(
  Employee_id INT,
  username VARCHAR(20) UNIQUE,
  psswd VARCHAR(50) NOT NULL,
  SSN VARCHAR(7) NOT NULL,
  First_Name VARCHAR(20) NOT NULL,
  Last_Name VARCHAR(20) NOT NULL,
  Dateofbirth DATE NOT NULL,
  CONSTRAINT admin_pk PRIMARY KEY(Employee_id)
);

CREATE TABLE COURSE_OFFERING(
COURSE_ID VARCHAR(6),
FACULTY_NAME VARCHAR(50),
SEMESTER VARCHAR(10) NOT NULL,
DAYS_OF_WEEK VARCHAR(20) NOT NULL,
START_TIME interval day (0) to second(0) NOT NULL,
END_TIME interval day (0) to second(0) NOT NULL,
CLASS_SIZE INT NOT NULL,
NUMBER_OF_ENROLLED INT DEFAULT 0,
WAITLIST_SIZE INT NOT NULL,
WAITLISTED INT DEFAULT 0,
CONSTRAINT COURSE_OFFERING_PK PRIMARY KEY(COURSE_ID,FACULTY_NAME,SEMESTER),
CONSTRAINT SEMESTER_CHECK CHECK(SEMESTER IN ('Fall','Spring')),
CONSTRAINT class_size_constraint CHECK(NUMBER_OF_ENROLLED <= CLASS_SIZE),
CONSTRAINT waitlist_size_constraint CHECK(WAITLISTED <= WAITLISTED)
);

CREATE TABLE COURSES(
COURSE_ID VARCHAR(6),
COURSE_NAME VARCHAR(50) NOT NULL,
DEPT_NAME VARCHAR(10) NOT NULL,
LEVEL_CLASS VARCHAR(10) NOT NULL,
GPA_REQ NUMERIC,
PRE_REQ_COURSES VARCHAR(50),
SPCL_APPROVAL_REQ VARCHAR(3) NOT NULL,
CREDITS INT NOT NULL,
CONSTRAINT COURSE_PK PRIMARY KEY(COURSE_ID),
CONSTRAINT LEVEL_CLASS_CHECK CHECK(LEVEL_CLASS IN ('Undergraduate', 'Graduate'))
);

CREATE TABLE CREDITMAP(
level_class VARCHAR(20),
residency_class VARCHAR(20),
min_credits INT NOT NULL,
max_credits INT NOT NULL,
Cost_per_credit INT NOT NULL,
CONSTRAINT cmap_pk PRIMARY KEY(level_class, residency_class)
);

CREATE TABLE DEADLINE_ENFORCED(
STATUS INT DEFAULT 0
);

CREATE TABLE ENROLLMENT(
STUDENT_ID INT,
COURSE_ID VARCHAR(10),
FACULTY VARCHAR(50),
SEMESTER VARCHAR(10),
LETTER_GRADE VARCHAR(5),
CREDIT INT,
CONSTRAINT enrollment_pk PRIMARY KEY (Student_id, Course_id, FACULTY, Semester)
);

CREATE TABLE GRADEMAP(
LETTER_GRADE VARCHAR(2),
NUMBER_GRADE DECIMAL(3,2)
);

CREATE TABLE PREREQUISITE(
COURSE_ID VARCHAR(10),
PREREQUISITE_ID VARCHAR(10),
LETTER_GRADE VARCHAR(5),
NUMBER_GRADE DECIMAL(3,2),
CONSTRAINT prereq_pk PRIMARY KEY(COURSE_ID, PREREQUISITE_ID)
);

CREATE TABLE SPECIAL_PERMISSION(
CREATE_TIME TIMESTAMP,
STUDENT_ID INT,
COURSE_ID VARCHAR(10),
FACULTY VARCHAR(50),
SEMESTER VARCHAR(10),
APPROVAL_STATUS VARCHAR(20),
ADMIN_NAME VARCHAR(50),
DATE_OF_APPROVAL DATE,
CREDITS DECIMAL(3,2),
CONSTRAINT special_permission_pk PRIMARY KEY (Student_id, Course_id, FACULTY, Semester)
);

CREATE TABLE Student(
  Student_id INT,
  username VARCHAR(20) UNIQUE,
  psswd VARCHAR(50) NOT NULL,
  First_Name VARCHAR(20) NOT NULL,
  Last_Name VARCHAR(20) NOT NULL,
  email VARCHAR(30) NOT NULL,
  phone VARCHAR(10) NOT NULL,
  department VARCHAR(10) NOT NULL,
  level_class VARCHAR(20) NOT NULL,
  residency_class VARCHAR(20) NOT NULL,
  GPA DECIMAL(3,2) NOT NULL,
  Dateofbirth DATE NOT NULL,
  CONSTRAINT student_pk PRIMARY KEY(Student_id),
  CONSTRAINT level_c CHECK(level_class in ('Undergraduate', 'Graduate')),
  CONSTRAINT residency CHECK(residency_class in ('In-State', 'Out-State', 'International')),
  CONSTRAINT phone_length CHECK (LENGTH(phone) = 10)
);

CREATE TABLE STUDENTBILL(
Student_id INT,
Total_Amount INT DEFAULT 0,
Amount_Paid INT DEFAULT 0,
CONSTRAINT bill_fk FOREIGN KEY (Student_id) REFERENCES STUDENT(Student_id) ON DELETE CASCADE
);

CREATE TABLE STUDENTCREDIT(
Student_id INT,
min_credit INT DEFAULT 0,
max_credit INT DEFAULT 0,
current_credit INT DEFAULT 0,
tot_credit INT DEFAULT 0,
CREDIT_COST INT DEFAULT 0,
CONSTRAINT credit_fk FOREIGN KEY (Student_id) REFERENCES STUDENT(Student_id) ON DELETE CASCADE,
CONSTRAINT credit_limit_constraint CHECK (max_credit >= current_credit)
);

CREATE TABLE VARIABLE_CEDIT_COURSES(
COURSE_ID VARCHAR(6),
MIN_CREDIT INT NOT NULL,
MAX_CREDIT INT NOT NULL,
CONSTRAINT variable_pk PRIMARY KEY (COURSE_ID),
CONSTRAINT credit_check CHECK(MIN_CREDIT <= MAX_CREDIT)
);

CREATE TABLE WAITLIST(
STUDENT_ID INT,
COURSE_ID VARCHAR(10),
FACULTY VARCHAR(50),
SEMESTER VARCHAR(10),
WAITLIST_NUMBER INT,
DROP_COURSE VARCHAR(10),
CREDITS INT,
CONSTRAINT waitlist_pk PRIMARY KEY (Student_id, Course_id, FACULTY, Semester)
);



/************************************ TRIGGERS ************************************/
CREATE OR REPLACE TRIGGER enrollment_trigger
AFTER INSERT ON ENROLLMENT
FOR EACH ROW
DECLARE TEMP1 INT;
BEGIN
UPDATE STUDENTCREDIT SET CURRENT_CREDIT = CURRENT_CREDIT + :new.credit 
  where STUDENT_ID = :new.STUDENT_ID;
SELECT CREDIT_COST INTO TEMP1 FROM STUDENTCREDIT WHERE STUDENT_ID = :new.STUDENT_ID;
UPDATE STUDENTBILL SET TOTAL_AMOUNT =TOTAL_AMOUNT + (TEMP1*:new.credit)
					 WHERE STUDENT_ID = :new.STUDENT_ID;
UPDATE COURSE_OFFERING SET NUMBER_OF_ENROLLED = NUMBER_OF_ENROLLED + 1 
	where course_id = :new.course_id and Semester = 'SPRING' 
						and Faculty_Name = :new.FACULTY;
END;
/


CREATE OR REPLACE TRIGGER enrollment_delete_triger
AFTER DELETE ON ENROLLMENT
FOR EACH ROW
DECLARE 
TEMP_COST INT;
BEGIN
UPDATE COURSE_OFFERING SET NUMBER_OF_ENROLLED = NUMBER_OF_ENROLLED - 1 
      WHERE course_id = :old.course_id and Semester = 'SPRING';
UPDATE STUDENTCREDIT SET CURRENT_CREDIT = CURRENT_CREDIT - :old.CREDIT
      WHERE STUDENT_ID = :old.STUDENT_ID;
SELECT CREDIT_COST INTO TEMP_COST FROM STUDENTCREDIT WHERE STUDENT_ID = :old.student_id;
UPDATE STUDENTBILL SET TOTAL_AMOUNT = TOTAL_AMOUNT - (TEMP_COST * :old.CREDIT)
      WHERE STUDENT_ID = :old.STUDENT_ID;
END;
/

TRIGGER waitlist_delete_trigger
AFTER DELETE ON WAITLIST
FOR EACH ROW
DECLARE TEMP_STATUS INT;
BEGIN
  SELECT STATUS INTO TEMP_STATUS FROM DEADLINE_ENFORCED; 
  IF (TEMP_STATUS = 0) THEN
  UPDATE COURSE_OFFERING SET WAITLISTED = WAITLISTED - 1 where course_id = :old.course_id and Semester = 'SPRING';
  END IF;
  IF( TEMP_STATUS = 1 )THEN
  UPDATE COURSE_OFFERING SET WAITLISTED = 0;
  END IF;
END;
/

TRIGGER waitlist_trigger
AFTER INSERT ON WAITLIST
FOR EACH ROW
BEGIN
UPDATE COURSE_OFFERING SET WAITLISTED = WAITLISTED + 1 where course_id = :new.course_id and Semester = 'SPRING';
END;
/


/************************************ PROCEDURES ************************************/

create or replace PROCEDURE calgpa(sid INT) 
AS
gpa1 DECIMAL(3,2);
BEGIN

SELECT SUM(EE.CREDIT * GM.NUMBER_GRADE)/SUM(EE.CREDIT) INTO gpa1 FROM ENROLLMENT EE, GRADEMAP GM
WHERE GM.LETTER_GRADE = EE.LETTER_GRADE and EE.STUDENT_ID = sid;

UPDATE STUDENT SET GPA = gpa1 WHERE STUDENT_ID = sid;
END;