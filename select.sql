SELECT * FROM COURSES;
SELECT * FROM COURSE_OFFERING;
SELECT * FROM PREREQUISITE;
SELECT * FROM Student;
SELECT * FROM Admins;
SELECT * FROM CREDITMAP;
SELECT * FROM STUDENTCREDIT;
SELECT * FROM GRADEMAP;
SELECT * FROM ENROLLMENT;
SELECT * FROM VARIABLE_CEDIT_COURSES;
SELECT * FROM STUDENTBILL;
SELECT * ENROLLMENT;
SELECT * SPECIAL_PERMISSION;
SELECT * WAITLIST;
SELECT * FROM STUDENT

DELETE STUDENTBILL;

UPDATE COURSES SET COURSE_NAME = 'VLSI II' WHERE COURSE_ID = 'CS421' ;

INSERT INTO PREREQUISITE VALUES('CS402', 'CS401', 'C', '2');

DELETE COURSE_OFFERING;
DELETE STUDENT;
UPDATE Student set residency_class = 'Out-State' where Student_id = 103;
UPDATE STUDENTCREDIT SET min_credit = 0 where student_id = 111;
DELETE FROM STUDENT WHERE Student_id = 233;
DELETE FROM STUDENTCREDIT WHERE Student_id = 111;

SELECT * FROM COURSE_OFFERING WHERE SEMESTER = 'SPRING' and (NUMBER_OF_ENROLLED < CLASS_SIZE or WAITLISTED < WAITLIST_SIZE);
DELETE FROM COURSE_OFFERING WHERE SEMESTER = 'SPRING' and COURSE_ID = 'CS525';
ALTER TABLE COURSE_OFFERING
ADD WAITLISTED INT DEFAULT 0;

UPDATE COURSE_OFFERING SET START_TIME = '+00 14:30:00.000000' where SEMESTER = 'SPRING' and course_id = 'CS525';
