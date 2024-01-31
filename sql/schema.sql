DROP DATABASE IF EXISTS atypon_grading;
CREATE DATABASE atypon_grading;
USE atypon_grading;
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);
CREATE TABLE courses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE NOT NULL,
    instructor_id INT,
    FOREIGN KEY (instructor_id) REFERENCES users (id)
);
-- composite primary keys don't work in Dao classes, so make grades have an id attribute
CREATE TABLE grades (
    id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT NOT NULL,
    student_id INT NOT NULL,
    grade_percent INT,
    UNIQUE (course_id, student_id),
    FOREIGN KEY (course_id) REFERENCES courses (id),
    FOREIGN KEY (student_id) REFERENCES users (id)
);