CREATE DATABASE  IF NOT EXISTS `zerli` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `zerli`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: zerli
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `complaint`
--

DROP TABLE IF EXISTS `complaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `complaint` (
  `complaintID` int(11) NOT NULL,
  `customerID` int(11) NOT NULL,
  `storeID` int(11) NOT NULL,
  `complaintDetails` varchar(250) DEFAULT NULL,
  `assigningDate` varchar(50) NOT NULL,
  `gotTreatment` int(11) DEFAULT '0',
  `gotRefund` int(11) DEFAULT '0',
  PRIMARY KEY (`complaintID`),
  KEY `custCOM_idx` (`customerID`),
  KEY `strCOM_idx` (`storeID`),
  CONSTRAINT `custCOM` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `strCOM` FOREIGN KEY (`storeID`) REFERENCES `store` (`storeID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaint`
--

LOCK TABLES `complaint` WRITE;
/*!40000 ALTER TABLE `complaint` DISABLE KEYS */;
INSERT INTO `complaint` VALUES (1,2468,1,NULL,'2011-11-11',0,0),(2,5326,3,NULL,'2011-11-11',0,0),(3,2468,2,NULL,'2011-12-12',0,0),(4,5326,1,NULL,'2011-07-07',0,0);
/*!40000 ALTER TABLE `complaint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `customerID` int(11) NOT NULL,
  `UserName` varchar(20) NOT NULL,
  `isSettlement` int(11) DEFAULT '0',
  `isMember` int(11) DEFAULT '0',
  PRIMARY KEY (`customerID`),
  KEY `USERNAME_FK_CUSTO_idx` (`UserName`),
  CONSTRAINT `USERNAME_FK_CUSTO` FOREIGN KEY (`UserName`) REFERENCES `user` (`UserName`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (2468,'customer1',0,0),(5326,'Customer',1,1);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `delivery_details`
--

DROP TABLE IF EXISTS `delivery_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `delivery_details` (
  `DeliveryID` int(11) NOT NULL AUTO_INCREMENT,
  `IDOrder` int(11) NOT NULL,
  `StoreID` int(11) NOT NULL,
  `dateOfSupply` date NOT NULL,
  `TimeOfSupply` time NOT NULL,
  `isInstant` int(11) DEFAULT '0',
  `deliveryPrice` double DEFAULT '10',
  PRIMARY KEY (`DeliveryID`),
  KEY `Store_idx` (`StoreID`),
  KEY `ORDER_ID_FK_DELIV_idx` (`IDOrder`),
  CONSTRAINT `ORDER_ID_FK_DELIV` FOREIGN KEY (`IDOrder`) REFERENCES `order` (`orderId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `delivery_details`
--

LOCK TABLES `delivery_details` WRITE;
/*!40000 ALTER TABLE `delivery_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `delivery_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `expertconclusions`
--

DROP TABLE IF EXISTS `expertconclusions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `expertconclusions` (
  `concID` int(11) NOT NULL AUTO_INCREMENT,
  `conclusionTxt` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`concID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `expertconclusions`
--

LOCK TABLES `expertconclusions` WRITE;
/*!40000 ALTER TABLE `expertconclusions` DISABLE KEYS */;
/*!40000 ALTER TABLE `expertconclusions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `orderId` int(11) NOT NULL AUTO_INCREMENT,
  `customerID` int(11) DEFAULT NULL,
  `supplyMethod` enum('PICKUP','DELIVERY') NOT NULL,
  `orderPrice` double DEFAULT '0',
  `greeting` varchar(150) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `orderTime` time DEFAULT NULL,
  `isPaid` int(11) DEFAULT '0',
  `storeID` int(11) DEFAULT NULL,
  PRIMARY KEY (`orderId`),
  KEY `customerID_FK_Oder_idx` (`customerID`),
  KEY `STOREID_FK_Order_idx` (`storeID`),
  CONSTRAINT `STOREID_FK_Order` FOREIGN KEY (`storeID`) REFERENCES `store` (`storeID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `customerID_FK_Oder` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment` (
  `paymentID` int(11) NOT NULL AUTO_INCREMENT,
  `customerID` int(11) NOT NULL,
  `creditCardNumber` varchar(16) DEFAULT NULL,
  `balance` double DEFAULT '0',
  PRIMARY KEY (`paymentID`),
  KEY `Customer_ID-FK_PAY_idx` (`customerID`),
  CONSTRAINT `Customer_ID-FK_PAY` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `productID` int(11) NOT NULL AUTO_INCREMENT,
  `productName` varchar(45) NOT NULL,
  `productType` enum('CUSTOMIZED','BOUQUET') NOT NULL,
  `productDescription` varchar(100) DEFAULT NULL,
  `price` double DEFAULT '0',
  `quantity` int(11) DEFAULT '0',
  `dominantColor` enum('RED','GREEN','YELLOW','BLUE','BLACK','WHITE','PURPLE') DEFAULT NULL,
  `StoreID` int(11) DEFAULT NULL,
  PRIMARY KEY (`productID`),
  KEY `STORE_ID_PRODUCT_idx` (`StoreID`),
  CONSTRAINT `STORE_ID_PRODUCT` FOREIGN KEY (`StoreID`) REFERENCES `store` (`storeID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Rose','CUSTOMIZED','beutiful flower',10.5,10,'RED',1),(2,'Dahlia ','CUSTOMIZED','I love this flower!',7.25,5,'PURPLE',3),(3,'Sunflower','CUSTOMIZED','shiny like sunshine',5,7,'YELLOW',2),(4,'Wedding','BOUQUET','for white widding',50,60,'WHITE',3),(5,'Girl Friend flowers','BOUQUET','for you girlfriend',40,0,'BLUE',1),(6,'be happy','BOUQUET','just for fun',100,4,'GREEN',1);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_in_order`
--

DROP TABLE IF EXISTS `product_in_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product_in_order` (
  `ProductInOrderID` int(11) NOT NULL AUTO_INCREMENT,
  `OrderID` int(11) DEFAULT NULL,
  `productID` int(11) DEFAULT NULL,
  `totalPrice` double DEFAULT '0',
  PRIMARY KEY (`ProductInOrderID`),
  KEY `OrderID_idx` (`OrderID`),
  KEY `IDProduct_idx` (`productID`),
  CONSTRAINT `IDProduct` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `OrderID` FOREIGN KEY (`OrderID`) REFERENCES `order` (`orderId`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_in_order`
--

LOCK TABLES `product_in_order` WRITE;
/*!40000 ALTER TABLE `product_in_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_in_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reports`
--

DROP TABLE IF EXISTS `reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reports` (
  `IDreport` int(11) NOT NULL,
  `quar` int(11) DEFAULT NULL,
  `shop` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `sum` int(11) DEFAULT NULL,
  PRIMARY KEY (`IDreport`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reports`
--

LOCK TABLES `reports` WRITE;
/*!40000 ALTER TABLE `reports` DISABLE KEYS */;
INSERT INTO `reports` VALUES (1,1,'Acre','Bouquet',55),(2,1,'Haifa','Bouquet',3),(3,1,'Tel Aviv','Bouquet',2),(4,2,'Acre','Bouquet',33),(5,2,'Haifa','Bouquet',5),(6,2,'Tel Aviv','Bouquet',5),(7,3,'Acre','Bouquet',55),(8,3,'Haifa','Bouquet',56),(9,3,'Tel Aviv','Bouquet',6),(10,4,'Acre','Bouquet',88),(11,4,'Haifa','Bouquet',33),(12,4,'Tel Aviv','Bouquet',34),(13,1,'Acre','Single',25),(14,1,'Haifa','Single',62),(15,1,'Tel Aviv','Single',14),(16,2,'Acre','Single',3),(17,2,'Haifa','Single',35),(18,2,'Tel Aviv','Single',35),(19,3,'Acre','Single',78),(20,3,'Haifa','Single',99),(21,3,'Tel Aviv','Single',6),(22,4,'Acre','Single',47),(23,4,'Haifa','Single',4),(24,4,'Tel Aviv','Single',2),(25,NULL,'null','null',NULL),(26,NULL,NULL,'null',NULL);
/*!40000 ALTER TABLE `reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store` (
  `storeID` int(11) NOT NULL,
  `branchName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`storeID`),
  UNIQUE KEY `branchName_UNIQUE` (`branchName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES (3,'Acre'),(1,'Haifa'),(2,'Karmiel');
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscription` (
  `subscriptionID` int(11) NOT NULL AUTO_INCREMENT,
  `customerID` int(11) DEFAULT NULL,
  `type` enum('NONE','MONTHLY','YEARLY') DEFAULT NULL,
  `expDate` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`subscriptionID`),
  KEY `CUSTID_FK_SUBSC_idx` (`customerID`),
  CONSTRAINT `CUSTID_FK_SUBSC` FOREIGN KEY (`customerID`) REFERENCES `customer` (`customerID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `subscription`
--

LOCK TABLES `subscription` WRITE;
/*!40000 ALTER TABLE `subscription` DISABLE KEYS */;
INSERT INTO `subscription` VALUES (1,5326,'MONTHLY','31/01/2018');
/*!40000 ALTER TABLE `subscription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survey_answer`
--

DROP TABLE IF EXISTS `survey_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survey_answer` (
  `numSurvey` int(11) NOT NULL,
  `answer1` int(11) DEFAULT NULL,
  `answer2` int(11) DEFAULT NULL,
  `answer3` int(11) DEFAULT NULL,
  `answer4` int(11) DEFAULT NULL,
  `answer5` int(11) DEFAULT NULL,
  `answer6` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey_answer`
--

LOCK TABLES `survey_answer` WRITE;
/*!40000 ALTER TABLE `survey_answer` DISABLE KEYS */;
INSERT INTO `survey_answer` VALUES (1,4,6,6,7,8,10),(1,1,2,5,5,2,3);
/*!40000 ALTER TABLE `survey_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `survey_question`
--

DROP TABLE IF EXISTS `survey_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `survey_question` (
  `numSurvey` int(11) NOT NULL,
  `question1` text,
  `question2` text,
  `question3` text,
  `question4` text,
  `question5` text,
  `question6` text,
  PRIMARY KEY (`numSurvey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `survey_question`
--

LOCK TABLES `survey_question` WRITE;
/*!40000 ALTER TABLE `survey_question` DISABLE KEYS */;
INSERT INTO `survey_question` VALUES (1,'bla bla1','bla bla2','bla bla3','bla bla4','bla bla 5','bla bla6'),(2,'abc','cdb','ahj','iao','oqpe','lfad'),(3,'shela1','shela2','shela3','shela4','shela5','shela6');
/*!40000 ALTER TABLE `survey_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `UserName` varchar(20) NOT NULL,
  `Password` varchar(8) NOT NULL,
  `userID` int(9) DEFAULT NULL,
  `FirstName` varchar(45) DEFAULT NULL,
  `LastName` varchar(45) DEFAULT NULL,
  `ConnectionStatus` enum('Online','Offline','Blocked') DEFAULT NULL,
  `Permission` enum('Customer','StoreManager','StoreEmployee','Expert','CustomerService','SystemManager','CompanyEmployee','CompanyManager') DEFAULT NULL,
  `Phone` varchar(10) DEFAULT NULL,
  `Gender` varchar(2) DEFAULT NULL,
  `Email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('CompanyEmployee','123',187346837,'Od','Misho','Offline','CompanyEmployee','1984891878','M','psods@walla.co.il'),('CompanyManager','1',444444,'Hector','Notes','Offline','CompanyManager','54754754',NULL,NULL),('customer','123',305337990,'Tomer','Arzuan','Offline','Customer','0526751403','M','Tomerarzu@gmail.com'),('customer1','123',832947347,'walla','sababa','Offline','Customer','508867884','M','irir@yahoo.com'),('CustomerService','123',123455432,'Elinor','faddol','Offline','CustomerService','5468641818','F','Eli_nor@gmail.com'),('Expert','123',777888999,'Ido','Kalir','Offline','Expert','0245645665','M','IdoKal@gmail.com'),('StoreEmployee','123',444555666,'Edo','Notes','Offline','StoreEmployee','0532394820','M','EdoNotes@gmail.com'),('StoreManager','123',111222333,'Matan','sabag','Offline','StoreManager','0526514879','M','blabla@gmail.com'),('SystemManager','123',567899876,'stam','misho','Offline','SystemManager','8948565184','F','sdasd@gmail.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-01-15 15:07:44
