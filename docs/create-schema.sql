CREATE DATABASE  IF NOT EXISTS `dev_challenge` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `dev_challenge`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dev_challenge
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.31-MariaDB

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
-- Table structure for table `conta`
--

DROP TABLE IF EXISTS `conta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `saldo` decimal(11,2) NOT NULL,
  `limite_saque_diario` decimal(11,2) NOT NULL,
  `ativo` tinyint(1) NOT NULL,
  `data_criacao` datetime NOT NULL,
  `tipo` int(11) NOT NULL,
  `pessoa_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_conta_pessoa_idx` (`pessoa_id`),
  CONSTRAINT `fk_conta_pessoa` FOREIGN KEY (`pessoa_id`) REFERENCES `pessoa` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pessoa`
--

DROP TABLE IF EXISTS `pessoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pessoa` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cpf` varchar(11) NOT NULL,
  `nome` varchar(255) NOT NULL,
  `data_nascimento` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `pessoa_cpf_uindex` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transacao`
--

DROP TABLE IF EXISTS `transacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transacao` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `valor` decimal(11,2) NOT NULL,
  `data` datetime NOT NULL,
  `tipo` int(11) NOT NULL,
  `conta_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_transacao_conta_idx` (`conta_id`),
  CONSTRAINT `fk_transacao_conta` FOREIGN KEY (`conta_id`) REFERENCES `conta` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-01  8:19:23
