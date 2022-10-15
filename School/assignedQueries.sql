--Find all groups with less or equal students’ number

DROP TABLE IF EXISTS school.groups_students_count;
CREATE TABLE school.groups_students_count (group_id, student_count)
AS
SELECT group_id, COUNT (group_id) FROM school.students GROUP BY group_id;
DELETE FROM school.groups_students_count WHERE student_count > 20;
SELECT school.groups.group_id, group_name FROM school.groups INNER JOIN school.groups_students_count
ON school.groups.group_id = school.groups_students_count.group_id;

--Find all students related to the course with the given name

SELECT school.students.first_name, school.students.last_name 
      FROM school.students_courses 
INNER JOIN school.students ON school.students.student_id = school.students_courses.student_id
     WHERE school.students_courses.course_id = (SELECT course_id FROM school.courses WHERE course_name = 'Mathematics');

--Add a new student

INSERT INTO school.students (student_id, first_name, last_name) VALUES (201, 'John', 'Smith');

--Delete a student by the STUDENT_ID

DELETE FROM school.students WHERE student_id = 201;

--Add a student to the course (from a list)

INSERT INTO school.students_courses (student_id, course_id) VALUES (3, 5);

--Remove the student from one of their courses.
     
DELETE FROM school.students_courses WHERE student_id = 3 AND course_id = 5;
