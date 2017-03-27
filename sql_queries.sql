DROP TABLE Student;
DROP TABLE Admins;

CREATE TABLE Student(
  Student_id INT,
  psswd VARCHAR(50) NOT NULL,
  First_Name VARCHAR(20) NOT NULL,
  Last_Name VARCHAR(20) NOT NULL,
  email VARCHAR(30) NOT NULL,
  level_class INT NOT NULL,
  residency_class INT NOT NULL,
  GPA NUMERIC NOT NULL,
  Dateofbirth DATE NOT NULL,
  CONSTRAINT student_pk PRIMARY KEY(Student_id)
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

SELECT * FROM Student;
INSERT INTO Student
VALUES (100 ,'secret', 'Anuraag', 'Motiwale', 'asmotiwa@ncsu.edu',1, 2, 0.0, TO_DATE('1989-12-09','YYYY-MM-DD'));

SELECT * FROM Admins;
INSERT INTO Admins
VALUES(101, 'admin1', 1234567, 'Abhishek', 'Singh', TO_DATE('1989-12-09','YYYY-MM-DD'));

INSERT INTO Admins
VALUES(102, 'admin2', 1234567, 'Parag', 'Nakhwa', TO_DATE('1989-12-09','YYYY-MM-DD'));


