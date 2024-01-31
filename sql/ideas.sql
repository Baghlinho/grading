select s.student_id, g.grade_percent from grades g where g.course_id = 1;
select g.course_id, g.grade_percent from students s inner join grades g on s.id = g.student_id;
select c.name, g.grade_percent from courses c inner join grades g on c.id = g.course_id where g.student_id=1;
select c.name, max(g.grade_percent) as avg_grade, count(g.student_id) as number_of_students from courses c inner join grades g group by c.name;
select c.name, max(g.grade_percent) as avg_grade, count(g.student_id) as number_of_students from courses c inner join grades g where c.instructor_id = 1 group by c.name;
select c.name, g.grade_percent, AVG(g.grade_percent) from courses c inner join grades g on c.id = g.course_id where g.student_id=1 group by c.name;
select *, AVG(g.grade_percent) from courses c inner join grades g on c.id = g.course_id where g.student_id=1 group by c.name;

//            String sql = "SELECT c.id, c.name, g.grade_percent " +
//                    "FROM courses c " +
//                    "INNER JOIN grades g " +
//                    "ON c.id = g.course_id " +
//                    "WHERE g.student_id = ?;"