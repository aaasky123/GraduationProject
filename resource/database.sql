-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: workTurnManagementSystem
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company` (
  `id` int NOT NULL AUTO_INCREMENT,
  `company_id` bigint NOT NULL,
  `company_name` varchar(32) NOT NULL,
  `admin_id` bigint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,1,'apple',1),(2,1,'apple',2),(4,2,'google',1),(5,3,'sony',1);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `file` (
  `id` int NOT NULL AUTO_INCREMENT,
  `file_name` varchar(256) DEFAULT NULL,
  `new_file_name` varchar(256) DEFAULT NULL,
  `path` varchar(256) DEFAULT NULL,
  `work_id` bigint DEFAULT NULL,
  `work_flow_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
INSERT INTO `file` VALUES (19,'新建文本文档.txt','4c2c7333-dd8f-49b8-a302-0880757d7229新建文本文档.txt','D:\\graduationbackup\\4c2c7333-dd8f-49b8-a302-0880757d7229新建文本文档.txt',19,1),(20,'新建文本文档.txt','42cf201f-c440-4d62-8d43-a07f3f412722新建文本文档.txt','D:\\graduationbackup\\42cf201f-c440-4d62-8d43-a07f3f412722新建文本文档.txt',19,1);
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `helper`
--

DROP TABLE IF EXISTS `helper`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `helper` (
  `id` int NOT NULL AUTO_INCREMENT,
  `helper_user_id` bigint DEFAULT NULL,
  `work_id` bigint DEFAULT NULL,
  `work_flow_id` bigint DEFAULT NULL,
  `company_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `helper`
--

LOCK TABLES `helper` WRITE;
/*!40000 ALTER TABLE `helper` DISABLE KEYS */;
INSERT INTO `helper` VALUES (1,1,1,2,1);
/*!40000 ALTER TABLE `helper` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notifications` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_user_id` bigint DEFAULT NULL,
  `notifications_content` varchar(256) DEFAULT NULL,
  `create_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (1,NULL,'123456',1617616771),(2,1,'123456789',1617617302),(3,1,'987654321',1617641848),(4,1,'13579',1617641858);
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `create_admin_id` bigint NOT NULL,
  `create_permission_time` varchar(256) NOT NULL,
  `permission_value` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (1,1,1,'2021-04-14 01:51:38',3),(5,13,1,'2021-04-13 21:55:28',2);
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `turn_user`
--

DROP TABLE IF EXISTS `turn_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `turn_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `turn_user_id` bigint NOT NULL,
  `company_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `turn_user`
--

LOCK TABLES `turn_user` WRITE;
/*!40000 ALTER TABLE `turn_user` DISABLE KEYS */;
INSERT INTO `turn_user` VALUES (4,1,2,1);
/*!40000 ALTER TABLE `turn_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `number` bigint NOT NULL AUTO_INCREMENT,
  `password` varchar(64) NOT NULL,
  `picture` varchar(256) DEFAULT NULL,
  `email` varchar(32) NOT NULL,
  `company_id` int DEFAULT NULL,
  `employee_id` int DEFAULT NULL,
  `realName` varchar(32) NOT NULL,
  PRIMARY KEY (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'123456',NULL,'1143341609@qq.com',1,1,'xiaoya'),(2,'123',NULL,'1@qq.com',1,6,'aimer'),(3,'456',NULL,'2@qq.com',1,4,'milet'),(11,'987',NULL,'1143341609@qq.com',NULL,NULL,'aimyon'),(13,'654',NULL,'8@qq.com',1,5,'jay');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work`
--

DROP TABLE IF EXISTS `work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `work` (
  `work_id` bigint NOT NULL AUTO_INCREMENT,
  `work_name` varchar(64) NOT NULL,
  `create_user_id` bigint NOT NULL,
  `create_time` varchar(256) NOT NULL,
  `end_time` varchar(256) DEFAULT NULL,
  `company_id` int NOT NULL,
  `state` int DEFAULT '0',
  `start_description` text NOT NULL,
  `end_description` text,
  `confidential` int DEFAULT NULL,
  PRIMARY KEY (`work_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work`
--

LOCK TABLES `work` WRITE;
/*!40000 ALTER TABLE `work` DISABLE KEYS */;
INSERT INTO `work` VALUES (1,'418pista',1,'1616593668','1617001729',1,0,'制造418pista',NULL,0),(6,'mcl',1,'1616593668','',1,0,'35','',1),(7,'mcl',1,'1616634233','',1,0,'123','',0),(10,'w11',1,'1616751435','',1,0,'w11','',0),(11,'w12',1,'1616751734','',1,0,'w12','',0),(12,'w1',1,'1616751801','',1,0,'w1','',0),(13,'rb5',1,'1616752000','',1,0,'rb5','',0),(14,'rb6',1,'1616850788','',1,0,'rb6','',0),(15,'hongniu',1,'1616931197','',1,0,'hongniu','',0),(16,'lec16',1,'1616932726','',1,0,'lec16','',0),(17,'russell',1,'1616933551','',1,0,'russell','',0),(18,'bruce',1,'1616934003','1617001728',1,1,'bruce','',0),(19,'xbox',1,'1617455714','',1,0,'ps5','',0);
/*!40000 ALTER TABLE `work` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work_flow`
--

DROP TABLE IF EXISTS `work_flow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `work_flow` (
  `work_id` bigint NOT NULL,
  `work_flow_id` bigint NOT NULL,
  `company_id` int DEFAULT NULL,
  `host_id` bigint NOT NULL,
  `turn_reason` varchar(256) DEFAULT NULL,
  `start_time` varchar(256) DEFAULT NULL,
  `end_time` varchar(256) DEFAULT NULL,
  `state` int DEFAULT NULL,
  `work_flow_name` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work_flow`
--

LOCK TABLES `work_flow` WRITE;
/*!40000 ALTER TABLE `work_flow` DISABLE KEYS */;
INSERT INTO `work_flow` VALUES (6,1,1,1,NULL,'1616593668','1616593669',NULL,'gm'),(6,2,1,2,'wc','1616593670',NULL,NULL,'bs'),(1,1,1,1,NULL,'1616751336','1617156557',1,'w11'),(1,2,1,2,NULL,'1616751435',NULL,0,'w11'),(1,3,1,3,NULL,'1616751734',NULL,0,'w12'),(1,4,1,1,NULL,'1616751801',NULL,0,'w1'),(13,1,1,1,NULL,'1616752000',NULL,0,'rb5'),(14,1,1,1,NULL,'1616850788',NULL,0,'rb6'),(15,1,1,1,NULL,'1616931197',NULL,0,'hongniu'),(16,1,1,1,NULL,'1616932726',NULL,0,'lec16'),(17,1,1,1,NULL,'1616933551','1617413264',1,'russell'),(18,1,1,1,NULL,'1616934003',NULL,0,'bruce'),(19,1,1,1,'ps5','1617455714',NULL,0,'xbox');
/*!40000 ALTER TABLE `work_flow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work_flow_mark`
--

DROP TABLE IF EXISTS `work_flow_mark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `work_flow_mark` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `work_id` bigint DEFAULT NULL,
  `work_flow_id` int DEFAULT NULL,
  `create_user_id` bigint DEFAULT NULL,
  `mark_content` varchar(256) DEFAULT NULL,
  `mark_time` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work_flow_mark`
--

LOCK TABLES `work_flow_mark` WRITE;
/*!40000 ALTER TABLE `work_flow_mark` DISABLE KEYS */;
INSERT INTO `work_flow_mark` VALUES (1,16,1,NULL,'123456789',NULL),(2,16,1,NULL,'987654321',NULL),(3,16,1,1,'13579',1617419372),(4,16,1,1,'13579',1617419734),(5,16,1,1,'246810',1617419906),(6,19,1,1,'Charles leclerc',1617543036);
/*!40000 ALTER TABLE `work_flow_mark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `work_manage`
--

DROP TABLE IF EXISTS `work_manage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `work_manage` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `manage_id` bigint DEFAULT NULL,
  `update_time` varchar(256) DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work_manage`
--

LOCK TABLES `work_manage` WRITE;
/*!40000 ALTER TABLE `work_manage` DISABLE KEYS */;
INSERT INTO `work_manage` VALUES (1,2,1,'1617553377',1),(2,3,1,'20210414025423',1);
/*!40000 ALTER TABLE `work_manage` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-15  8:55:14
