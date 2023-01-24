DROP DATABASE  IF EXISTS `hotel_management_app_security`;

CREATE DATABASE  IF NOT EXISTS `hotel_management_app_security`;
USE `hotel_management_app_security`;


--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` char(68) NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


-- Dumping data for table `users`
-- The passwords are encrypted using BCrypt
-- A generation tool is available at: https://bcrypt-generator.com
-- The passwords are same as the usernames

INSERT INTO `users` 
VALUES 
('employee1','{bcrypt}$2a$12$y14XdsDAO7GjhfQ6fdWcBO1LZSYNYY95UGXLi/AsTPq4xhsbdfaeK',1),
('employee2','{bcrypt}$2a$12$dFIZMuWl/TWvJ5YYa2K/Ge9buyQk26yn2KDk4cCkXgqsvU56M14Fm',1),
('manager1','{bcrypt}$2a$12$nOKpnqRGUyNL67YEsWQxCefgfAxZa7tvkv0X54WF.MCWnH9485HH2',1),
('admin','{bcrypt}$2a$12$8C5PGZS9RtPtGX.7oM9sjeJ55cbtTljLp4pktRQzbtEURakLxVq3K',1);


--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


--
-- Dumping data for table `authorities`
--

INSERT INTO `authorities` 
VALUES 
('employee1','ROLE_EMPLOYEE'),
('employee2','ROLE_EMPLOYEE'),
('manager1','ROLE_MANAGER'),
('admin','ROLE_ADMIN');