/********************************** INSERT **********************************/
/* Course Table */
INSERT INTO COURSES VALUES('CSC401', 'Introduction to Computer Science', 'CSC',
'Undergraduate', 0, 'No', 'No', 3);
INSERT INTO COURSES VALUES('CSC510', 'Database', 'CSC',
'Graduate', 0, 'No', 'No', 3);
INSERT INTO COURSES VALUES('CSC515', 'Software Engineering', 'CSC',
'Graduate', 0, 'No', 'No', 3);
INSERT INTO COURSES VALUES('CSC520', 'Internet Protocols', 'CSC',
'Graduate', 0, 'No', 'No', 3);
INSERT INTO COURSES VALUES('CSC525', 'Independent Study', 'CSC',
'Graduate', 0, 'No', 'No', 3);
INSERT INTO COURSES VALUES('ECE420', 'VLSI', 'ECE',
'Undergraduate', 0, 'No', 'No', 3);

INSERT INTO COURSES VALUES('CSC402', 'Numerical Methods', 'CSC',
'Undergraduate', 0, 'No', 'No', 3);
INSERT INTO COURSES VALUES('CSC505', 'Algorithms', 'CSC',
'Graduate', 0, 'No', 'No', 3);
INSERT INTO COURSES VALUES('CSC521', 'Cloud Computing', 'CSC',
'Graduate', 0, 'No', 'No', 3);
INSERT INTO COURSES VALUES('CSC525', 'Independent Study', 'CSC',
'Graduate', 0, 'No', 'No', 3);
INSERT INTO COURSES VALUES('ECE420', 'VLSI', 'ECE',
'Undergraduate', 0, 'No', 'No', 3);

DELETE FROM COURSES WHERE COURSE_ID = 'CSC402';
DELETE PREREQUISITE;

ALTER TABLE COURSES;

/* Course Offering Table */

/*enrollment*/
desc enrollment;
insert into enrollment values(101,'CE420','DR. H','FALL','A',3);

insert into enrollment values(102,'CS510','DR. KEMAFOR','FALL','B+',3);

insert into enrollment values(102,'CS515','DR. X','FALL','B+',3);

insert into enrollment values(103,'CS515','DR. X','FALL','A',3);

insert into enrollment values(103,'CS520','DR. A','FALL','A-',3);

insert into enrollment values(103,'CS530','DR. F','FALL','A+',3);

insert into enrollment values(104,'CS510','DR. KEMAFOR','FALL','A',3);

insert into enrollment values(104,'CS515','DR. X','FALL','B+',3);

insert into enrollment values(104,'CS525','DR. MX','FALL','A+',3);




insert into studentbill values(101,1200,0);

insert into studentbill values(102,0,0);

insert into studentbill values(103,0,0);

insert into studentbill values(104,0,0);


update student set gpa=4.0 where student_id=101;

update student set gpa=3.33 where student_id=102;

update student set gpa=4.0 where student_id=103;

update student set gpa=3.88 where student_id=104;

delete from student where student_id=111;










