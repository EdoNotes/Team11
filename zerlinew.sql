-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: zerli0
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
  `complaintID` int(11) NOT NULL AUTO_INCREMENT,
  `customerID` int(11) NOT NULL,
  `storeID` int(11) NOT NULL,
  `complaintDetails` varchar(250) DEFAULT NULL,
  `assigningDate` date NOT NULL,
  `gotTreatment` int(11) DEFAULT '0',
  `gotRefund` int(11) DEFAULT '0',
  PRIMARY KEY (`complaintID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `complaint`
--

LOCK TABLES `complaint` WRITE;
/*!40000 ALTER TABLE `complaint` DISABLE KEYS */;
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
INSERT INTO `customer` VALUES (2468,'Customer1',0,0),(5326,'Customer',1,1);
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
  PRIMARY KEY (`productID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'Rose','CUSTOMIZED','beutiful flower',10.5,10,'RED'),(2,'Dahlia ','CUSTOMIZED','I love this flower!',7.25,5,'PURPLE'),(3,'Sunflower','CUSTOMIZED','shiny like sunshine',5,7,'YELLOW'),(4,'Wedding','BOUQUET','for white widding',50,60,'WHITE'),(5,'Girl Friend flowers','BOUQUET','for you girlfriend',40,0,'BLUE'),(6,'be happy','BOUQUET','just for fun',100,4,'GREEN');
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
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store` (
  `storeID` int(11) NOT NULL AUTO_INCREMENT,
  `branchName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`storeID`),
  UNIQUE KEY `branchName_UNIQUE` (`branchName`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES (3,'Acre'),(1,'Hiafa'),(2,'Kramiel');
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
  `Permission` enum('Customer','StoreManager','StoreEmployee','Expert','CostomerServiece','SystemManager','CompanyEmployee','CompanyManager') DEFAULT NULL,
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
INSERT INTO `user` VALUES ('CompanyEmployee','123',187346837,'Od','Misho','Offline','CompanyEmployee','1984891878','M','psods@walla.co.il'),('CostomerServiece','123',123455432,'Elinor','faddol','Offline','CostomerServiece','5468641818','F','Eli_nor@gmail.com'),('customer','123',305337990,'Tomer','Arzuan','Offline','Customer','0526751403','M','Tomerarzu@gmail.com'),('Customer1','123',832947347,'walla','sababa','Offline','Customer','508867884','M','irir@yahoo.com'),('Expert','123',777888999,'Ido','Kalir','Offline','Expert','0245645665','M','IdoKal@gmail.com'),('StoreEmployee','123',444555666,'Edo','Notes','Offline','StoreEmployee','0532394820','M','EdoNotes@gmail.com'),('StoreManager','123',111222333,'Matan','sabag','Offline','StoreManager','0526514879','M','blabla@gmail.com'),('SystemManager','123',567899876,'stam','misho','Offline','SystemManager','8948565184','F','sdasd@gmail.com');
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

-- Dump completed on 2018-01-09 21:50:32
