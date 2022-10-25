CREATE SCHEMA IF NOT EXISTS school
     AUTHORIZATION postgres;

DROP TABLE IF EXISTS school.courses CASCADE;

CREATE TABLE IF NOT EXISTS school.courses
(
	course_id SERIAL PRIMARY KEY,
    course_name VARCHAR(20) NOT NULL,
    course_description VARCHAR(200)
);

DROP TABLE IF EXISTS school.groups CASCADE;

CREATE TABLE IF NOT EXISTS school.groups
(
    group_id SERIAL PRIMARY KEY,
    group_name VARCHAR(5) NOT NULL
);

DROP TABLE IF EXISTS school.students CASCADE;

CREATE TABLE IF NOT EXISTS school.students
(
    student_id SERIAL PRIMARY KEY,
    group_id INT,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
	FOREIGN KEY (group_id)
		REFERENCES school.groups(group_id)
			ON DELETE SET NULL
);

DROP TABLE IF EXISTS school.students_courses CASCADE;

CREATE TABLE IF NOT EXISTS school.students_courses
(
	student_id INT NOT NULL,
	course_id INT NOT NULL,
	FOREIGN KEY (student_id)
		REFERENCES school.students(student_id),
	FOREIGN KEY (course_id)
		REFERENCES school.courses(course_id),
	UNIQUE (student_id, course_id)
);