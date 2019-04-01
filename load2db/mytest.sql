/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.23 : Database - mytest
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mytest` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mytest`;

/*Table structure for table `staff` */

DROP TABLE IF EXISTS `staff`;

CREATE TABLE `staff` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `dept` varchar(100) NOT NULL,
  `name` varchar(50) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `age` int(11) NOT NULL,
  `status` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32665 DEFAULT CHARSET=utf8;

/*Data for the table `staff` */

insert  into `staff`(`id`,`dept`,`name`,`gender`,`age`,`status`) values (1,'保卫科','周兴发','男',21,'在职'),(2,'机动1部','弗利萨','男',28,'在职'),(3,'机动1部','科尔兴','男',22,'在职'),(4,'超级赛亚族','悟空','男',30,'在职'),(5,'超级赛亚族','悟饭','男',19,'在职'),(6,'绿族','短笛','男',35,'在职'),(7,'绿族','小短笛','男',18,'在职'),(8,'龟先流','龟仙人','男',108,'退休'),(9,'龟先流','小林','男',30,'在职');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
