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
-- Table structure for table `mokit`
--

DROP TABLE IF EXISTS `mokit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mokit` (
  `mokin_numero` int NOT NULL,
  `mokin_nimi` varchar(100) DEFAULT NULL,
  `pinta_ala` double DEFAULT NULL,
  `onko_varattu` tinyint(1) DEFAULT NULL,
  `lisaominaisuudet` text,
  PRIMARY KEY (`mokin_numero`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mokit`
--

LOCK TABLES `mokit` WRITE;
/*!40000 ALTER TABLE `mokit` DISABLE KEYS */;
INSERT INTO `mokit` VALUES (1,'Rantapoukama',45,0,'Wheelchair accessible'),(2,'Sädekehä',85,1,'Kahvinkeitin, ilmastointi'),(3,'Tähtilampi',45,1,'Wheelchair accessible'),(4,'Unelma',23.5,0,'Tuulikaappi'),(5,'Ei tietoja',0,0,''),(6,'KarhunpesäInen',45,0,'Hyvät sängyt'),(7,'Ei tietoja',0,0,''),(8,'Serveriluola',5,1,'servereitä'),(9,'Muusikko',67,1,'ilmapatjat'),(10,'Mökkikakkonen',100,1,'Pitkät portaat');
/*!40000 ALTER TABLE `mokit` ENABLE KEYS */;
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
