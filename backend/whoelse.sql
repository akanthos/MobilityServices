-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 22, 2015 at 07:46 PM
-- Server version: 5.6.24
-- PHP Version: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `whoelse`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_comments`
--

CREATE TABLE IF NOT EXISTS `tbl_comments` (
  `commentId` int(11) NOT NULL,
  `userIdPost` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `comment` text NOT NULL,
  `rating` enum('1','2','3','4','5') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_routes`
--

CREATE TABLE IF NOT EXISTS `tbl_routes` (
  `routeId` int(11) NOT NULL,
  `startlat` double NOT NULL,
  `startlong` double NOT NULL,
  `endlat` double NOT NULL,
  `endlong` double NOT NULL,
  `time` time NOT NULL,
  'weekday' enum('1','2','3','4','5','6','7') NOT NULL 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_routePoint`
--

CREATE TABLE IF NOT EXISTS `tbl_routePoints` (
  `pointId` int(11) NOT NULL,
  `routeId` int(11) NOT NULL,
  `lat` double NOT NULL,
  `long` double NOT NULL,
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_users`
--

CREATE TABLE IF NOT EXISTS `tbl_users` (
  `userId` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `age` int(20) NOT NULL,
  `gender` varchar(10) NOT NULL,
  `about` text,
  `token` varchar(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_matchings`
--

CREATE TABLE IF NOT EXISTS `tbl_matchings` (
  `routeId1` int(11) NOT NULL,
  `routeId2` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_comments`
--
ALTER TABLE `tbl_comments`
  ADD PRIMARY KEY (`commentId`), ADD UNIQUE KEY `FOREIGNKEY` (`userId`), ADD UNIQUE KEY `FOREIGNKEYPOST` (`userIdPost`);

--
-- Indexes for table `tbl_routes`
--
ALTER TABLE `tbl_routes`
  ADD PRIMARY KEY (`routeId`), ADD UNIQUE KEY `FOREIGNKEY` (`userId`);

--
-- Indexes for table `tbl_routePoint`
--
ALTER TABLE `tbl_routePoints`
  ADD PRIMARY KEY (`pointId`), ADD UNIQUE KEY `FOREIGNKEY` (`routeId`);

--
-- Indexes for table `tbl_users`
--
ALTER TABLE `tbl_users`
  ADD PRIMARY KEY (`userId`), ADD UNIQUE KEY `email` (`email`(50));

--
-- Indexes for table `tbl_matching`
--
ALTER TABLE `tbl_matchings`
  ADD PRIMARY KEY (`routeId1`, `routeId2`), ADD UNIQUE KEY `FOREIGNKEY1` (`routeId1`), ADD UNIQUE KEY `FOREIGNKEY2` (`routeId2`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_comments`
--
ALTER TABLE `tbl_comments`
  MODIFY `commentId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tbl_routes`
--
ALTER TABLE `tbl_routes`
  MODIFY `routeId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_routePoint`
--
ALTER TABLE `tbl_routePoints`
  MODIFY `pointId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_users`
--

ALTER TABLE `tbl_users`
  MODIFY `userId` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_comments`
--
ALTER TABLE `tbl_comments`
ADD CONSTRAINT `tbl_comments_ibfk_1` FOREIGN KEY (`userIdPost`) REFERENCES `tbl_users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `tbl_comments_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `tbl_users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tbl_routes`
--
ALTER TABLE `tbl_routes`
ADD CONSTRAINT `tbl_routes_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `tbl_users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tbl_routePoint`
--
ALTER TABLE `tbl_routePoints`
ADD CONSTRAINT `tbl_routePoint_ibfk_1` FOREIGN KEY (`routeId`) REFERENCES `tbl_routes` (`routeId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tbl_matchings`
--
ALTER TABLE `tbl_matchings`
ADD CONSTRAINT `tbl_matchings_ibfk_1` FOREIGN KEY (`routeId1`) REFERENCES `tbl_routes` (`routeId`) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT `tbl_matchings_ibfk_2` FOREIGN KEY (`routeId2`) REFERENCES `tbl_routes` (`routeId`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
