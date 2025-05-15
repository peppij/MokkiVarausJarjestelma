-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mokkijarjestelma
-- ------------------------------------------------------
-- Server version	9.2.0

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
-- Table structure for table `lasku`
--

DROP TABLE IF EXISTS `lasku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lasku` (
  `laskunumero` int NOT NULL,
  `summa` double DEFAULT NULL,
  `onko_maksettu` tinyint DEFAULT NULL,
  `lisatiedot` mediumtext,
  `laskun_asiakas` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`laskunumero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lasku`
--

LOCK TABLES `lasku` WRITE;
/*!40000 ALTER TABLE `lasku` DISABLE KEYS */;
INSERT INTO `lasku` VALUES (1,100,1,'-','Marjatta'),(2,300,0,'Asiakkaalla maksusopimus','Bebsi'),(3,150,0,'Maksaa osamaksulla','Esa'),(4,2000,1,'-','Pena'),(5,400,1,'Asiakas on köyhä','Sari'),(6,200,0,'Firman piikkiin?','Joni'),(7,800,0,'Asiakkaalla maksusopimus','Marja'),(8,0,0,'','Ei tietoja'),(9,100,1,'-','Tiina'),(10,22,1,'Julkkis alennus','Niilo');
/*!40000 ALTER TABLE `lasku` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-15 17:54:49
