-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: artwalk
-- ------------------------------------------------------
-- Server version	8.0.32-0ubuntu0.20.04.2

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `achievements`
--

DROP TABLE IF EXISTS `achievements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `achievements` (
  `achievements_id` int NOT NULL AUTO_INCREMENT,
  `icon` varchar(255) NOT NULL,
  `detail` varchar(50) DEFAULT NULL,
  `achievement` int NOT NULL,
  `category` varchar(10) NOT NULL,
  PRIMARY KEY (`achievements_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `achievements`
--

LOCK TABLES `achievements` WRITE;
/*!40000 ALTER TABLE `achievements` DISABLE KEYS */;
/*!40000 ALTER TABLE `achievements` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `user_id` varchar(50) NOT NULL,
  `password` varchar(20) NOT NULL,
  `refresh_token` varchar(255) NOT NULL,
  `user_authority` varchar(10) NOT NULL DEFAULT 'ROLE_ADMIN',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('ssafy@admin.com','ssafy1234','eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzc2FmeUBhZG1pbi5jb20iLCJhdXRoIjoiUk9MRV9BRE1JTiIsImV4cCI6MTY4MTc3ODM2NX0.rxyNJv7iTMCsCmE2fJX1YBzOJF6QO9XWN-P2nJyG51KyDKJ3496t8rOePPK2JV8JCixpLz3bqZW8-Wqyvk2cbg','ROLE_ADMIN');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deleteuser`
--

DROP TABLE IF EXISTS `deleteuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deleteuser` (
  `user_id` varchar(50) NOT NULL,
  `nickname` varchar(30) NOT NULL,
  `profile` varchar(255) DEFAULT NULL,
  `level` int NOT NULL,
  `exp` int NOT NULL,
  `user_authority` varchar(10) NOT NULL DEFAULT 'ROLE_USER',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deleteuser`
--

LOCK TABLES `deleteuser` WRITE;
/*!40000 ALTER TABLE `deleteuser` DISABLE KEYS */;
/*!40000 ALTER TABLE `deleteuser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follow` (
  `follow_id` int NOT NULL AUTO_INCREMENT,
  `from_user` varchar(50) NOT NULL,
  `to_user` varchar(50) NOT NULL,
  PRIMARY KEY (`follow_id`,`from_user`),
  KEY `FK_USER_TO_FOLLOW_1` (`from_user`),
  CONSTRAINT `FK_USER_TO_FOLLOW_1` FOREIGN KEY (`from_user`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `record`
--

DROP TABLE IF EXISTS `record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `record` (
  `record_id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `geometry` varchar(255) DEFAULT NULL,
  `distance` double NOT NULL DEFAULT '0',
  `duration` double NOT NULL DEFAULT '0',
  `creation` datetime NOT NULL,
  `title` varchar(40) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `recent_image` varchar(255) DEFAULT NULL,
  `link` varchar(10) DEFAULT NULL,
  `edit_link` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`record_id`,`user_id`),
  KEY `FK_USER_TO_RECORD_1` (`user_id`),
  CONSTRAINT `FK_USER_TO_RECORD_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=222 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `record`
--

LOCK TABLES `record` WRITE;
/*!40000 ALTER TABLE `record` DISABLE KEYS */;
/*!40000 ALTER TABLE `record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `route`
--

DROP TABLE IF EXISTS `route`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `route` (
  `route_id` int NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `maker` varchar(50) NOT NULL,
  `distance` double NOT NULL DEFAULT '0',
  `duration` double NOT NULL DEFAULT '0',
  `geometry` varchar(255) DEFAULT NULL,
  `creation` datetime NOT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `title` varchar(16) DEFAULT '나의 경로',
  PRIMARY KEY (`route_id`,`user_id`),
  KEY `FK_USER_TO_ROUTE_1` (`user_id`),
  CONSTRAINT `FK_USER_TO_ROUTE_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `route`
--

LOCK TABLES `route` WRITE;
/*!40000 ALTER TABLE `route` DISABLE KEYS */;
/*!40000 ALTER TABLE `route` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` varchar(50) NOT NULL,
  `nickname` varchar(30) NOT NULL,
  `profile` varchar(255) DEFAULT NULL,
  `level` int NOT NULL DEFAULT '0',
  `exp` int NOT NULL DEFAULT '0',
  `refresh_token` varchar(255) NOT NULL,
  `user_authority` varchar(10) NOT NULL DEFAULT 'ROLE_USER',
  `reg_date` datetime DEFAULT NULL,
  `recent_access` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_achievements`
--

DROP TABLE IF EXISTS `user_achievements`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_achievements` (
  `user_id` varchar(30) NOT NULL,
  `achievements_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`achievements_id`),
  KEY `FK_ACHIEVEMENTS_TO_USER_ACHIEVEMENTS_1` (`achievements_id`),
  CONSTRAINT `FK_ACHIEVEMENTS_TO_USER_ACHIEVEMENTS_1` FOREIGN KEY (`achievements_id`) REFERENCES `achievements` (`achievements_id`),
  CONSTRAINT `FK_USER_TO_USER_ACHIEVEMENTS_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_achievements`
--

LOCK TABLES `user_achievements` WRITE;
/*!40000 ALTER TABLE `user_achievements` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_achievements` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-17  9:57:05
