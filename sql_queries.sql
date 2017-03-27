DROP TABLE Student;
DROP TABLE Administrator;

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

CREATE TABLE Administrator(
  Employee_id INT,
  psswd VARCHAR(50) NOT NULL,
  SSN INT NOT NULL,
  First_Name VARCHAR(20) NOT NULL,
  Last_Name VARCHAR(20) NOT NULL,
  Dateofbirth DATE NOT NULL,
  CONSTRAINT admin_pk PRIMARY KEY(Employee_id)
);