CREATE DATABASE login_db;  -- create db
USE login_db;

-- USERS  (existing table – kept as-is)

CREATE TABLE users (
 id INT AUTO_INCREMENT PRIMARY KEY,
 username VARCHAR(50),
 password VARCHAR(50)
);
SHOW TABLES;


-- STUDENTS
CREATE TABLE IF NOT EXISTS students (
    id         INT AUTO_INCREMENT PRIMARY KEY,9
    name       VARCHAR(100) NOT NULL,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(100) NOT NULL,
    email      VARCHAR(100),
    department VARCHAR(100),
    year       INT
);

--  TEACHERS

CREATE TABLE IF NOT EXISTS teachers (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(100) NOT NULL,
    email      VARCHAR(100),
    department VARCHAR(100),
    subject    VARCHAR(100)
);


--  ATTENDANCE
-- ─────────────────────────────────────────
CREATE TABLE IF NOT EXISTS attendance (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    student_id   INT NOT NULL,
    subject      VARCHAR(100),
    date         DATE,
    status       ENUM('Present', 'Absent') DEFAULT 'Present',
    FOREIGN KEY (student_id) REFERENCES students(id)
);
 
-- ─────────────────────────────────────────
--  ASSIGNMENTS
-- ─────────────────────────────────────────
CREATE TABLE IF NOT EXISTS assignments (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    student_id  INT NOT NULL,
    title       VARCHAR(200),
    subject     VARCHAR(100),
    deadline    DATE,
    status      ENUM('Pending', 'Completed') DEFAULT 'Pending',
    FOREIGN KEY (student_id) REFERENCES students(id)
);
 
-- ─────────────────────────────────────────
--  SCHOLARSHIPS
-- ─────────────────────────────────────────
CREATE TABLE IF NOT EXISTS scholarships (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(200),
    description TEXT,
    deadline    DATE,
    amount      DECIMAL(10,2),
    eligibility VARCHAR(255)
);

 
 -- SAMPLE DATA
 INSERT INTO students (name, username, password, email, department, year) VALUES
('Arun Kumar',   'arun',   'arun123',   'arun@uni.edu',   'CSE', 2),
('Priya Rajan',  'priya',  'priya123',  'priya@uni.edu',  'IT',  3);
 
 INSERT INTO teachers (name, username, password, email, department, subject) VALUES
('Dr. Meena',    'meena',  'meena123',  'meena@uni.edu',  'CSE', 'OOSE'),
('Prof. Ramesh', 'ramesh', 'ramesh123', 'ramesh@uni.edu', 'IT',  'DBMS');

INSERT INTO scholarships (title, description, deadline, amount, eligibility) VALUES
('Merit Scholarship',   'For students with GPA > 8.5',          '2026-06-30', 25000.00, 'GPA > 8.5'),
('SC/ST Scholarship',   'Government scholarship for SC/ST',      '2026-07-15', 15000.00, 'SC/ST category'),
('Sports Excellence',   'For students with state-level sports',  '2026-08-01', 10000.00, 'State-level sports');

INSERT INTO attendance (student_id, subject, date, status) VALUES
(1, 'OOSE', '2026-04-01', 'Present'),
(1, 'OOSE', '2026-04-02', 'Absent'),
(1, 'DBMS', '2026-04-01', 'Present'),
(1, 'DBMS', '2026-04-02', 'Present');
 
 INSERT INTO assignments (student_id, title, subject, deadline, status) VALUES
(1, 'ER Diagram Submission',   'DBMS', '2026-04-15', 'Pending'),
(1, 'UML Diagrams',            'OOSE', '2026-04-20', 'Pending'),
(1, 'Mini Project Phase 1',    'OOSE', '2026-05-01', 'Completed');



 SELECT * FROM assignments;
SELECT * FROM attendance;
 
 