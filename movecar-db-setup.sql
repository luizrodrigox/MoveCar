CREATE DATABASE movecar_db; 
CREATE USER 'movecar'@'localhost' IDENTIFIED BY 'movecarpass'; 
GRANT ALL PRIVILEGES ON movecar_db.* TO 'movecar'@'localhost'; 
FLUSH PRIVILEGES;