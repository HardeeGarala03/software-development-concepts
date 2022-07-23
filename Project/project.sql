-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: project
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `dissolution`
--

DROP TABLE IF EXISTS `dissolution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dissolution` (
  `dissolutionPartner1Id` int NOT NULL,
  `dissolutionPartner2Id` int NOT NULL,
  KEY `dissolutionPartner1_idx` (`dissolutionPartner1Id`),
  KEY `dissolutionPartner2_idx` (`dissolutionPartner2Id`),
  CONSTRAINT `dissolutionPartner1` FOREIGN KEY (`dissolutionPartner1Id`) REFERENCES `person` (`id`),
  CONSTRAINT `dissolutionPartner2` FOREIGN KEY (`dissolutionPartner2Id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dissolution`
--

LOCK TABLES `dissolution` WRITE;
/*!40000 ALTER TABLE `dissolution` DISABLE KEYS */;
INSERT INTO `dissolution` VALUES (32,33);
/*!40000 ALTER TABLE `dissolution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `filedetails`
--

DROP TABLE IF EXISTS `filedetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `filedetails` (
  `id` int NOT NULL AUTO_INCREMENT,
  `filelocation` varchar(500) DEFAULT NULL,
  `filename` varchar(45) NOT NULL,
  `mediaDate` date DEFAULT NULL,
  `location` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `province` varchar(45) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `year` int DEFAULT NULL,
  `month` int DEFAULT NULL,
  `day` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `filelocation_UNIQUE` (`filelocation`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filedetails`
--

LOCK TABLES `filedetails` WRITE;
/*!40000 ALTER TABLE `filedetails` DISABLE KEYS */;
INSERT INTO `filedetails` VALUES (1,' C:Userssrcfamily.png','family.png',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(3,'C:FileIdentifier.java','FileIdentifier.java',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,'C:file.jpg','file.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,'C:userFileIdentifier.java','FileIdentifier.java',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,'C://user//FileIdentifier.java','C://user//FileIdentifier.java',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10,'C:useFileIdentifier.java','FileIdentifier.java',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11,'C:\\used\\FileIdentifier.java','FileIdentifier.java','2018-04-19','Dalhousie','Halifax','NovaScotia','Canada',2021,3,19),(12,'D:\\used\\FileIdentifier.java','FileIdentifier.java',NULL,NULL,NULL,NULL,'Canada',NULL,NULL,NULL),(14,' C:Usersafamily.png','family.png','2021-05-13','Tokyo',NULL,NULL,NULL,NULL,NULL,NULL),(15,' C:\\Users\\a\\family.png','family.png','2021-05-13','Jakarta',NULL,NULL,NULL,NULL,NULL,NULL),(28,' C:\\Users\\b\\flower.png','flower.png','2011-02-20','Zanzari',NULL,NULL,NULL,NULL,NULL,NULL),(29,' C:\\Users\\c\\park.png','park.png','2009-01-26','Kankaria',NULL,NULL,NULL,NULL,NULL,NULL),(30,' C:\\Users\\d\\arc.jpeg','arc.jpeg','2019-05-07','Pavagadh',NULL,NULL,NULL,NULL,NULL,NULL),(31,' C:\\Users\\e\\sun.jpg','sun.jpg','2018-04-13','Delhi',NULL,NULL,NULL,NULL,NULL,NULL),(32,' C:\\Users\\a\\moon.png','moon.png','2017-08-13','Manali',NULL,NULL,NULL,NULL,NULL,NULL),(33,' C:\\Users\\a\\trend.png','trend.png','2016-07-19','Pavagadh',NULL,NULL,NULL,NULL,NULL,NULL),(34,'D:usedfestive.jpg','D:usedfestive.jpg',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(36,'D:\\used\\charm.jpg','charm.jpg',NULL,'bali','Sunda','Lesser','Indonesia',2002,7,9);
/*!40000 ALTER TABLE `filedetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mediatags`
--

DROP TABLE IF EXISTS `mediatags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mediatags` (
  `file_id` int NOT NULL,
  `tag` varchar(200) NOT NULL,
  KEY `media_id_idx` (`file_id`),
  CONSTRAINT `media_id` FOREIGN KEY (`file_id`) REFERENCES `filedetails` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mediatags`
--

LOCK TABLES `mediatags` WRITE;
/*!40000 ALTER TABLE `mediatags` DISABLE KEYS */;
INSERT INTO `mediatags` VALUES (7,''),(7,'foodie'),(7,''),(6,'travel'),(1,'google photos'),(1,'restaurant'),(1,'happy'),(2,'nature'),(2,'restaurant'),(1,'butterfly'),(3,'google photos'),(3,'park'),(3,'flowers'),(6,'nature'),(5,'flowers'),(5,'happy'),(5,'park'),(5,'google photos'),(28,'google photos'),(28,'restaurant'),(28,'happy'),(29,'nature'),(29,'restaurant'),(30,'butterfly'),(30,'google photos'),(30,'park'),(30,'flowers'),(32,'nature'),(33,'flowers'),(33,'happy'),(33,'park'),(33,'google photos');
/*!40000 ALTER TABLE `mediatags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `note_detail`
--

DROP TABLE IF EXISTS `note_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `note_detail` (
  `person_note_id` int NOT NULL,
  `person_notes` varchar(500) DEFAULT NULL,
  `entryDate` date DEFAULT NULL,
  `entryTime` time DEFAULT NULL,
  KEY `note_id` (`person_note_id`),
  CONSTRAINT `note_id` FOREIGN KEY (`person_note_id`) REFERENCES `person` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `note_detail`
--

LOCK TABLES `note_detail` WRITE;
/*!40000 ALTER TABLE `note_detail` DISABLE KEYS */;
INSERT INTO `note_detail` VALUES (4,'notes added',NULL,NULL),(43,'notes added',NULL,NULL),(43,'notes 2 added',NULL,NULL),(43,'notes added',NULL,NULL),(43,'notes 2 added',NULL,NULL),(2,'Stonehenge','2018-09-11','01:05:11'),(2,'Petra','2017-12-02','10:11:13'),(11,'Jordan','2017-10-22','11:51:59'),(3,'Colosseum','2021-12-15','03:34:13');
/*!40000 ALTER TABLE `note_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `occupation`
--

DROP TABLE IF EXISTS `occupation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `occupation` (
  `person_id` int NOT NULL,
  `occupationType` varchar(45) NOT NULL,
  PRIMARY KEY (`person_id`),
  CONSTRAINT `person_occupation` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `occupation`
--

LOCK TABLES `occupation` WRITE;
/*!40000 ALTER TABLE `occupation` DISABLE KEYS */;
INSERT INTO `occupation` VALUES (6,'software developer'),(8,'software developer'),(9,'data engineer'),(11,'data analyst'),(12,'DBA'),(15,'technical assistant');
/*!40000 ALTER TABLE `occupation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parentchild`
--

DROP TABLE IF EXISTS `parentchild`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parentchild` (
  `parent_id` int NOT NULL,
  `child_id` int NOT NULL,
  KEY `parentId_idx` (`parent_id`),
  KEY `childId_idx` (`child_id`),
  CONSTRAINT `childId` FOREIGN KEY (`child_id`) REFERENCES `person` (`id`),
  CONSTRAINT `parentId` FOREIGN KEY (`parent_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parentchild`
--

LOCK TABLES `parentchild` WRITE;
/*!40000 ALTER TABLE `parentchild` DISABLE KEYS */;
INSERT INTO `parentchild` VALUES (1,2),(1,3),(2,5),(2,6),(2,7),(3,8),(3,9),(8,10),(8,11),(4,12),(12,13),(10,14),(10,15),(4,3),(1,2),(1,3),(2,5),(2,6),(2,7),(3,8),(3,9),(8,10),(8,11),(4,12),(12,13),(10,14),(10,15),(4,3);
/*!40000 ALTER TABLE `parentchild` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partner`
--

DROP TABLE IF EXISTS `partner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partner` (
  `partner1id` int NOT NULL,
  `partner2id` int NOT NULL,
  KEY `partner1_idx` (`partner1id`),
  KEY `partner2_idx` (`partner2id`),
  CONSTRAINT `partner1` FOREIGN KEY (`partner1id`) REFERENCES `person` (`id`),
  CONSTRAINT `partner2` FOREIGN KEY (`partner2id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partner`
--

LOCK TABLES `partner` WRITE;
/*!40000 ALTER TABLE `partner` DISABLE KEYS */;
INSERT INTO `partner` VALUES (9,11),(16,19);
/*!40000 ALTER TABLE `partner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `peopleinfile`
--

DROP TABLE IF EXISTS `peopleinfile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `peopleinfile` (
  `person_id` int NOT NULL,
  `file_id` int NOT NULL,
  KEY `personInFile_idx` (`person_id`),
  KEY `fileFK_idx` (`file_id`),
  CONSTRAINT `fileFK` FOREIGN KEY (`file_id`) REFERENCES `filedetails` (`id`),
  CONSTRAINT `personInFile` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `peopleinfile`
--

LOCK TABLES `peopleinfile` WRITE;
/*!40000 ALTER TABLE `peopleinfile` DISABLE KEYS */;
INSERT INTO `peopleinfile` VALUES (9,11),(12,11),(30,11),(16,11),(23,11),(26,11),(12,14),(19,14),(8,12);
/*!40000 ALTER TABLE `peopleinfile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `person` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `birthdate` date DEFAULT NULL,
  `birthlocation` varchar(45) DEFAULT NULL,
  `deathdate` date DEFAULT NULL,
  `deathlocation` varchar(45) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'hardee','2001-12-02',NULL,NULL,NULL,NULL),(2,'devarsh','2001-12-12',NULL,NULL,NULL,NULL),(3,'vishwa',NULL,NULL,NULL,NULL,NULL),(4,'khushi','2004-12-12',NULL,NULL,NULL,NULL),(5,'aksh',NULL,NULL,NULL,NULL,NULL),(6,'neha','2004-12-12','DC',NULL,NULL,NULL),(7,'umang',NULL,NULL,NULL,NULL,NULL),(8,'sapna','2003-10-20','gujarat',NULL,NULL,NULL),(9,'nikunj','1997-09-29','pune',NULL,NULL,'M'),(10,'anirudh',NULL,NULL,NULL,NULL,NULL),(11,'nikki',NULL,NULL,NULL,NULL,NULL),(12,'megha',NULL,'pune','2020-05-01','chennai','F'),(13,'dev',NULL,NULL,NULL,NULL,NULL),(14,'vikram',NULL,NULL,NULL,NULL,NULL),(15,'ram','1968-10-03',NULL,NULL,NULL,NULL),(16,'krishna',NULL,NULL,NULL,NULL,NULL),(17,'west',NULL,NULL,NULL,NULL,NULL),(18,'merry',NULL,NULL,NULL,NULL,NULL),(19,'dia',NULL,NULL,NULL,NULL,NULL),(20,'jiya',NULL,NULL,NULL,NULL,NULL),(21,'deval',NULL,NULL,NULL,NULL,NULL),(22,'saurabh',NULL,NULL,NULL,NULL,NULL),(23,'Aumkar',NULL,NULL,NULL,NULL,NULL),(24,'zaraa',NULL,NULL,NULL,NULL,NULL),(25,'florence',NULL,NULL,NULL,NULL,NULL),(26,'allen',NULL,NULL,NULL,NULL,NULL),(27,'jenny',NULL,NULL,NULL,NULL,NULL),(28,'jenifer',NULL,NULL,NULL,NULL,NULL),(29,'michael',NULL,NULL,NULL,NULL,NULL),(30,'anshi',NULL,NULL,NULL,NULL,NULL),(31,'anshul',NULL,NULL,NULL,NULL,NULL),(32,'akshul',NULL,NULL,NULL,NULL,NULL),(33,'ananya',NULL,NULL,NULL,NULL,NULL),(34,'anushka',NULL,NULL,NULL,NULL,NULL),(35,'virat',NULL,NULL,NULL,NULL,NULL),(36,'shardul',NULL,NULL,NULL,NULL,NULL),(37,'sania',NULL,NULL,NULL,NULL,NULL),(38,'aaradhya',NULL,NULL,NULL,NULL,NULL),(39,'arjun',NULL,NULL,NULL,NULL,NULL),(40,'amitabh',NULL,NULL,NULL,NULL,NULL),(41,'abhi',NULL,NULL,NULL,NULL,NULL),(42,'kaushal',NULL,NULL,NULL,NULL,NULL),(43,'jiya',NULL,NULL,NULL,NULL,NULL),(44,'ssd',NULL,NULL,NULL,NULL,NULL),(45,'anushka','1999-12-19',NULL,NULL,NULL,'M');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reference_detail`
--

DROP TABLE IF EXISTS `reference_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reference_detail` (
  `refer_id` int NOT NULL,
  `reference` varchar(45) DEFAULT NULL,
  `entryDate` date DEFAULT NULL,
  `entryTime` time DEFAULT NULL,
  KEY `reference_id_idx` (`refer_id`),
  CONSTRAINT `reference_id` FOREIGN KEY (`refer_id`) REFERENCES `person` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reference_detail`
--

LOCK TABLES `reference_detail` WRITE;
/*!40000 ALTER TABLE `reference_detail` DISABLE KEYS */;
INSERT INTO `reference_detail` VALUES (1,'taj','2020-11-25','13:30:40'),(2,'lothal','2019-05-22','23:40:41'),(2,'Machu Picchu','2019-05-22','23:50:11'),(11,'Cambodia','2021-12-15','02:31:22');
/*!40000 ALTER TABLE `reference_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-12-15  6:44:11
