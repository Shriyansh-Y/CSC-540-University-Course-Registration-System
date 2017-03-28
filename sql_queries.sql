DROP TABLE Student;
DROP TABLE Admins;
DROP TABLE CREDITCOST;
DROP TABLE CREDITMAP

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
  GPA NUMERIC NOT NULL,
  Dateofbirth DATE NOT NULL,
  CONSTRAINT student_pk PRIMARY KEY(Student_id),
  CONSTRAINT level_c CHECK(level_class in ('Undergraduate', 'Graduate')),
  CONSTRAINT residency CHECK(residency_class in ('In-State', 'Out-State', 'International'))
);

CREATE TABLE Admins(
  Employee_id INT,
  psswd VARCHAR(50) NOT NULL,
  SSN VARCHAR(7) NOT NULL,
  First_Name VARCHAR(20) NOT NULL,
  Last_Name VARCHAR(20) NOT NULL,
  Dateofbirth DATE NOT NULL,
  CONSTRAINT admin_pk PRIMARY KEY(Employee_id)
);

CREATE TABLE CREDITCOST(
level_class VARCHAR(20),
residency_class VARCHAR(20),
Cost_per_credit INT
);

CREATE TABLE CREDITMAP(
level_class VARCHAR(20),
residency_class VARCHAR(20),
min_credits INT,
max_credits INT
);

SELECT * FROM Student;
INSERT INTO Student
VALUES (100 , 'asmotiwa','secret', 'Anuraag', 'Motiwale', 'asmotiwa@ncsu.edu','9199170656','CS','Graduate', 'International', 0.0, TO_DATE('1989-12-09','YYYY-MM-DD'));

SELECT * FROM Admins;
INSERT INTO Admins
VALUES(101, 'admin1', 1234567, 'Abhishek', 'Singh', TO_DATE('1989-12-09','YYYY-MM-DD'));

INSERT INTO Admins
VALUES(102, 'admin2', 1234567, 'Parag', 'Nakhwa', TO_DATE('1989-12-09','YYYY-MM-DD'));

INSERT INTO CREDITCOST
VALUES('Graduate', 'In-State', 500);
INSERT INTO CREDITCOST
VALUES('Graduate', 'Out-State', 800);
INSERT INTO CREDITCOST
VALUES('Graduate', 'International', 1000);
INSERT INTO CREDITCOST
VALUES('Undergraduate', 'In-State', 400);
INSERT INTO CREDITCOST
VALUES('Undergraduate', 'Out-State', 700);
INSERT INTO CREDITCOST
VALUES('Underraduate', 'International', 900);

SELECT * FROM CREDITCOST;

INSERT INTO CREDITMAP
VALUES('Graduate', 'In-State', 0, 9);
INSERT INTO CREDITMAP
VALUES('Graduate', 'Out-State', 0, 9);
INSERT INTO CREDITMAP
VALUES('Graduate', 'International', 9, 9);
INSERT INTO CREDITMAP
VALUES('Undergraduate', 'In-State', 0, 12);
INSERT INTO CREDITMAP
VALUES('Undergraduate', 'Out-State', 0, 12);
INSERT INTO CREDITMAP
VALUES('Underraduate', 'International', 9,12);

SELECT * FROM CREDITMAP;