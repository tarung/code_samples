/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.6.21 : Database - attendance
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`attendance` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `attendance`;

/*Table structure for table `lecturers` */

DROP TABLE IF EXISTS `lecturers`;

CREATE TABLE `lecturers` (
  `lecturer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `gender` varchar(10) DEFAULT '',
  `category` varchar(10) NOT NULL,
  `email` varchar(100) NOT NULL DEFAULT '',
  `phone_number` varchar(15) DEFAULT '',
  `mobile_number` varchar(15) DEFAULT '',
  `date_of_birth` varchar(10) DEFAULT '',
  PRIMARY KEY (`lecturer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=556 DEFAULT CHARSET=latin1;

/*Data for the table `lecturers` */

insert  into `lecturers`(`lecturer_id`,`first_name`,`last_name`,`gender`,`category`,`email`,`phone_number`,`mobile_number`,`date_of_birth`) values (2,'FNUpdatr','LNUpdater','Female','lecturer','arer@gmail.com','123121234','123121234','01-01-2000'),(4,'Testing','Tester','Female','Admin','arer@gmail.com','1231231234','1231231234','11-11-2001'),(52,'tester','testwe','Female','Lecturer','arer@gmail.com','1231231234','1231231234','12/12/2000'),(54,'tester','testwe','Female','Lecturer','arer@gmail.com','1231231234','1231231234','12/12/2000'),(102,'tester','testwe','Female','Lecturer','arer@gmail.com','1231231234','1231231234','12/12/2000'),(104,'tester','testwe','Female','Lecturer','arer@gmail.com','1231231234','1231231234','12/12/2000'),(106,'tester','testwe','Female','Lecturer','arer@gmail.com','1231231234','1231231234','12/12/2000'),(108,'tester','testwe','Female','Lecturer','arer@gmail.com','1231231234','1231231234','12/12/2000'),(110,'tester','testwe','Female','Lecturer','arer@gmail.com','1231231234','1231231234','12/12/2000'),(112,'tester','testwe','Female','Lecturer','arer@gmail.com','1231231234','1231231234','12/12/2000'),(114,'tester','testwe','Female','Lecturer','arer@gmail.com','1231231234','1231231234','12/12/2000'),(116,'tester','testwe','Female','Lecturer','arer@gmail.com','1231231234','1231231234','12/12/2000'),(555,'Niks','Singh','Male','lecturer','arkogh@gmail.com','1023121023','1023121023','7-1-2015');

/*Table structure for table `modules` */

DROP TABLE IF EXISTS `modules`;

CREATE TABLE `modules` (
  `module_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `module_title` varchar(150) NOT NULL,
  `semester` varchar(20) NOT NULL,
  `program_id` bigint(20) NOT NULL,
  `lecture_id` bigint(20) NOT NULL,
  PRIMARY KEY (`module_id`),
  KEY `FK_program_module` (`program_id`),
  KEY `FK_lecturer_module` (`lecture_id`),
  CONSTRAINT `FK_lecturer_module` FOREIGN KEY (`lecture_id`) REFERENCES `lecturers` (`lecturer_id`),
  CONSTRAINT `FK_program_module` FOREIGN KEY (`program_id`) REFERENCES `programs` (`program_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

/*Data for the table `modules` */

insert  into `modules`(`module_id`,`module_title`,`semester`,`program_id`,`lecture_id`) values (1,'Database','semester1',1,2),(2,'Petroleum GeoScience','semester1',1,2),(3,'Java','semester1',1,2),(4,'ITIL','semester1',1,2),(5,'Data Visualization','semester2',1,2),(6,'Oil and Gas Engineering','semester2',1,2),(7,'Web Development','semester2',1,4),(8,'Data Analysis','semester2',1,4),(9,'Project Investigation','semester3',1,4),(10,'Final Project','semester3',1,4),(11,'Networking','semester3',1,4),(12,'Data Warehousing','semester3',1,2),(13,'Database','semester1',2,4),(14,'Data Algorithm','semester1',2,4),(15,'Java','semester1',2,4),(16,'ITIL','semester1',2,4),(17,'Data Visualization','semester2',2,2),(18,'Python','semester2',2,2),(19,'Web Development','semester2',2,2),(20,'Cloud Computing','semester2',2,2),(21,'Project Investigation','semester3',2,4),(22,'Final Project','semester3',2,4),(23,'Social Implication','semester3',2,4),(24,'Communication and Documentation','semester3',2,4);

/*Table structure for table `programs` */

DROP TABLE IF EXISTS `programs`;

CREATE TABLE `programs` (
  `program_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `program_name` varchar(150) NOT NULL,
  PRIMARY KEY (`program_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `programs` */

insert  into `programs`(`program_id`,`program_name`) values (1,'MSc. IT Oil and Gas'),(2,'MSc. Comupter Science'),(3,'MSc Finance');

/*Table structure for table `sequence` */

DROP TABLE IF EXISTS `sequence`;

CREATE TABLE `sequence` (
  `SEQ_NAME` varchar(50) NOT NULL,
  `SEQ_COUNT` decimal(38,0) DEFAULT NULL,
  PRIMARY KEY (`SEQ_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `sequence` */

insert  into `sequence`(`SEQ_NAME`,`SEQ_COUNT`) values ('SEQ_GEN','651');

/*Table structure for table `sessions` */

DROP TABLE IF EXISTS `sessions`;

CREATE TABLE `sessions` (
  `session_id` int(20) NOT NULL AUTO_INCREMENT,
  `status` varchar(10) DEFAULT '',
  `date_taken` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `session` varchar(10) DEFAULT '',
  `student_id` bigint(20) NOT NULL,
  `module_id` bigint(20) NOT NULL,
  PRIMARY KEY (`session_id`),
  KEY `FK_student_session` (`student_id`),
  KEY `FK_module_session_id` (`module_id`),
  CONSTRAINT `FK_module_session_id` FOREIGN KEY (`module_id`) REFERENCES `modules` (`module_id`),
  CONSTRAINT `FK_student_session` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=603 DEFAULT CHARSET=latin1;

/*Data for the table `sessions` */

insert  into `sessions`(`session_id`,`status`,`date_taken`,`session`,`student_id`,`module_id`) values (1,'Present','2015-08-07 06:02:20','Lab',1,1),(2,'Absent','2015-08-08 06:04:41','Lecture',1,1),(3,'Present','2015-08-04 06:02:20','Lab',1,1),(4,'Absent','2015-08-07 06:02:20','Lecture',1,1),(5,'Absent','2015-08-06 06:02:20','Lab',1,1),(6,'Absent','2015-08-05 06:02:20','Both',1,1),(402,'Present','2015-08-05 20:00:00','Lecture',4,13),(452,'Present','2015-08-09 20:00:00','Lecture',7,17),(502,'Absent','2015-08-07 20:00:00','Lecture',4,13),(602,'Absent','2015-08-10 20:00:00','Lab',557,13);

/*Table structure for table `students` */

DROP TABLE IF EXISTS `students`;

CREATE TABLE `students` (
  `student_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `gender` varchar(10) DEFAULT '',
  `program_id` bigint(20) NOT NULL,
  PRIMARY KEY (`student_id`),
  KEY `FK_student_program` (`program_id`),
  CONSTRAINT `FK_student_program` FOREIGN KEY (`program_id`) REFERENCES `programs` (`program_id`)
) ENGINE=InnoDB AUTO_INCREMENT=558 DEFAULT CHARSET=latin1;

/*Data for the table `students` */

insert  into `students`(`student_id`,`first_name`,`last_name`,`gender`,`program_id`) values (1,'Nik','Sin','Male',1),(2,'Rono','Bloom','Male',1),(3,'Debjyoti','Sengupta','Male',1),(4,'Ruchi','Joshi','Female',1),(5,'Neha','Sin','Female',1),(6,'Ank','Bhard','Male',2),(7,'Puneet','Sin','Male',2),(352,'Milind','Kokane','Female',2),(557,'Kamal','Sanghi','Male',1);

/*Table structure for table `user_credentials` */

DROP TABLE IF EXISTS `user_credentials`;

CREATE TABLE `user_credentials` (
  `user_credential_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL,
  `password` varchar(256) DEFAULT NULL,
  `lecturer_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_credential_id`),
  UNIQUE KEY `Unique` (`user_name`),
  KEY `FK_user_credentials_lecturer_id` (`lecturer_id`),
  CONSTRAINT `FK_user_credentials_lecturer_id` FOREIGN KEY (`lecturer_id`) REFERENCES `lecturers` (`lecturer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=557 DEFAULT CHARSET=latin1;

/*Data for the table `user_credentials` */

insert  into `user_credentials`(`user_credential_id`,`user_name`,`password`,`lecturer_id`) values (3,'username11','password12',2),(5,'username12','password12',4),(53,'username13','password12',52),(55,'username14','password12',54),(103,'username15','password12',102),(105,'userName16','password12',104),(107,'userName17','password12',106),(109,'userName18','password12',108),(111,'userName19','password12',110),(113,'userName20','password12',112),(115,'userName21','password12',114),(117,'userName22','password12',116),(556,'username1245','wkpUL4hOFERR+QY7eeeZTg',555);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
