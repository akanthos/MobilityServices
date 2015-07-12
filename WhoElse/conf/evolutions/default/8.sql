# --- !Ups

INSERT INTO `User` (userId,username,firstName,lastName,company,password,email,balance) VALUES (7,'user1','Thomas','Spahn','Public','42ef13e75a5c91d97f2caba697b9028c7d6bb523','thomas@hotmail.com',0.0);
INSERT INTO `User` (userId,username,firstName,lastName,company,password,email,balance) VALUES (8,'user2','Yannis','Stratakos','Public','42ef13e75a5c91d97f2caba697b9028c7d6bb523','yannis@hotmail.com',0.0);

INSERT INTO `RoutePattern` (routePatternId,userId,type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (7,7,'pattern','Marienplatz, Munich, Germany','Garching, Germany',48.137079,11.5760064,48.2488721,11.6532477,'08:00',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (8,7,'pattern','Garching, Germany','Marienplatz, Munich, Germany',48.2488721,11.6532477,48.137079,11.5760064,'17:00',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (9,8,'pattern','Sendlinger-Tor-Platz, Munich, Germany','Boltzmannstra�e, Garching, Germany',48.1337196,11.5666065,48.264838,11.6712126,'08:00',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (10,8,'pattern','Boltzmannstra�e, Garching, Germany','Sendlinger-Tor-Platz, Munich, Germany',48.264838,11.6712126,48.1337196,11.5666065,'17:00',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (11,8,'pattern','Clemensstra�e 118, 80796, Munich','Munich, Germany',48.16455699999999,11.5657065,48.1351253,11.5819806,'22:00',30,'',0.0,'Daily','Yes');

INSERT INTO `Matching` (matchingId,userId1,userId2,routePatternId1,routePatternId2,value) VALUES (5,8,7,9,7,0.989061162834593);
INSERT INTO `Matching` (matchingId,userId1,userId2,routePatternId1,routePatternId2,value) VALUES (6,8,7,10,8,0.989061162834593);

INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (1,'Ramersdorf-Perlach','M�nchen','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (2,'','Munich','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (3,'','Munich','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (4,'Ramersdorf-Perlach','M�nchen','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (5,'','Garching','Altstadt-Lehel','M�nchen');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (6,'','Garching','Sendling-Westpark','M�nchen');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (7,'','Garching','Ramersdorf-Perlach','Munich');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (8,'','Garching','Hochbr�ck','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (9,'Ramersdorf-Perlach','M�nchen','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (10,'Altstadt-Lehel','M�nchen','Hochbr�ck','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (11,'Altstadt-Lehel','M�nchen','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (12,'Altstadt-Lehel','M�nchen','','Ingolstadt');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (13,'','Ingolstadt','','Munich');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (14,'','Ingolstadt','','Munich');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (15,'Altstadt-Lehel','M�nchen','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (16,'Altstadt-Lehel','M�nchen','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (17,'Ramersdorf-Perlach','M�nchen','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (18,'Altstadt-Lehel','M�nchen','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (19,'Altstadt-Lehel','M�nchen','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (20,'Altstadt-Lehel','M�nchen','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (21,'Altstadt-Lehel','M�nchen','Hochbr�ck','Garching');

# --- !Downs

DELETE TABLE `User`
DELETE TABLE `RoutePattern`
DELETE TABLE `Matching`
DELETE TABLE `Search`