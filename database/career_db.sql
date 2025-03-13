CREATE DATABASE career_db;
USE career_db;

CREATE TABLE careers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    career VARCHAR(255),
    skills_required TEXT
);

INSERT INTO careers (career, skills_required) VALUES
('Software Developer', 'Java, Python, SQL'),
('Data Analyst', 'Excel, SQL, Python'),
('Cybersecurity Expert', 'Networking, Security, Python'),
('AI Engineer', 'Machine Learning, Python, AI');
