DROP DATABASE  IF EXISTS `hotel_management_app`;

CREATE DATABASE  IF NOT EXISTS `hotel_management_app`;
USE `hotel_management_app`;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
	`no` INTEGER NOT NULL AUTO_INCREMENT,
	`type` VARCHAR(25) NOT NULL,
	`price` FLOAT NOT NULL,
	PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=1 CHARSET=latin1;


--
-- Dumping data for table `room`
--

INSERT INTO `room`
VALUES
(101, 'Single Room', 94.50),
(102, 'Single Room', 94.50),
(103, 'Single Room', 94.50),
(104, 'Double Room', 129.50),
(105, 'Double Room', 129.50),
(106, 'Double Room', 129.50),
(107, 'Double Room', 129.50),
(108, 'Double Room', 129.50),
(109, 'Triple Room', 159.50),
(110, 'Triple Room', 159.50),
(201, 'Single Room', 110.0),
(202, 'Single Room', 110.0),
(203, 'Single Room', 110.0),
(204, 'Double Room', 140.0),
(205, 'Double Room', 140.0),
(206, 'Double Room', 140.0),
(207, 'Double Room', 140.0),
(208, 'Double Room', 140.0),
(209, 'Triple Room', 175.0),
(210, 'Triple Room', 175.0),
(1000, 'King Room', 499.90);



--
-- Table structure for table 'reservation'
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE `reservation` (
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`customer` VARCHAR(75) NOT NULL,
	`check_in` DATE NOT NULL,
	`check_out` DATE NOT NULL,
	`room_no` INTEGER NOT NULL,
    
	PRIMARY KEY (`id`),
    KEY `FK_ROOM_idx` (`room_no`),
    
	CONSTRAINT `FK_ROOM`
    FOREIGN KEY (`room_no`)
    REFERENCES `room` (`no`)
    
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;


--
-- Dumping data for table `reservation`
--

INSERT INTO `reservation`
VALUES
(1, 'Frankie Woodward', '2023-01-11', '2023-01-13', 101),
(2, 'Rueben Tucker', '2023/01/14', '2023/01/16', 101),
(3, 'Owain Mccall', '2023.01.16', '2023.01.21', 101),
(4, 'Zaid Stevens', '2023.01.21', '2023.01.23', 101),
(5, 'Ava-Rose Byrd', '2023.01.24', '2023.01.27', 101),
(6, 'Thomas House', '2023.01.28', '2023.02.01', 101),
(7, 'Joseph Dawson', '2023.02.04', '2023.02.10', 101),
(8, 'Jaya Rose', '2023.02.15', '2023.02.18', 101),
(9, 'Keiran Valencia', '2023.02.19', '2023.02.26', 101),
(10, 'Lillie Kramer', '2023.02.26', '2023.03.03', 101);
