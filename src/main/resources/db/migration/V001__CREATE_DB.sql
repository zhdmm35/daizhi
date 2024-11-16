CREATE DATABASE travel;
CREATE USER 'travel'@'localhost' IDENTIFIED BY 'mvLJUW9p32ad3FiH';
CREATE USER 'travel'@'%' IDENTIFIED BY 'mvLJUW9p32ad3FiH';
GRANT ALL ON travel.* TO 'travel'@'localhost';
GRANT ALL ON travel.* TO 'travel'@'%';
