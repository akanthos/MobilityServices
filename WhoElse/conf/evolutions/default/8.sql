# --- !Ups

INSERT INTO `User` (userId,username,firstName,lastName,company,password,email,balance) VALUES (7,'user1','Thomas','Spahn','Public','42ef13e75a5c91d97f2caba697b9028c7d6bb523','thomas@hotmail.com',0.0);
INSERT INTO `User` (userId,username,firstName,lastName,company,password,email,balance) VALUES (8,'user2','Yannis','Stratakos','Public','42ef13e75a5c91d97f2caba697b9028c7d6bb523','yannis@hotmail.com',0.0);


INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (7,7,'pattern','Marienplatz, Munich, Germany','Garching, Germany',48.137079,11.5760064,48.2488721,11.6532477,'08:00',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (8,7,'pattern','Garching, Germany','Marienplatz, Munich, Germany',48.2488721,11.6532477,48.137079,11.5760064,'17:00',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (9,8,'pattern','Sendlinger-Tor-Platz, Munich, Germany','Boltzmannstra?e, Garching, Germany',48.1337196,11.5666065,48.264838,11.6712126,'08:00',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (10,8,'pattern','Sendlinger-Tor-Platz, Munich, Germany','Boltzmannstra?e, Garching, Germany',48.1337196,11.5666065,48.264838,11.6712126,'08:20',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (11,8,'pattern','Boltzmannstraße, Garching, Germany','Sendlinger-Tor-Platz, Munich, Germany',48.264838,11.6712126,48.1337196,11.5666065,'17:00',30,'',0.0,'Daily','Yes');

INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (12,8,'pattern','Clemensstra?e 118, 80796, Munich','Munich, Germany',48.164557,11.5657065,48.1351253,11.5819806,'22:00',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (13,8,'pattern','Clemensstra?e 118, Munich, Germany','Marienplatz, Munich, Germany',48.164557,11.5657065,48.137079,11.5760064,'17:00',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (14,8,'pattern','Clemensstra?e 118, Munich, Germany','Max-Weber-Platz, Munich, Germany',48.164557,11.5657065,48.1355114,11.5978982,'17:00',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (15,8,'pattern','Clemensstra?e 118, Munich, Germany','Garching, Germany',48.164557,11.5657065,48.2488721,11.6532477,'17:00',30,'',0.0,'Daily','Yes');


INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (16,8,'pattern','Ingolstadt, Germany','Garching, Forschungszentrum, Garching, Germany',48.7665351,11.4257541,48.264671,11.671391,'16:20',45,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (17,8,'pattern','Garching, Forschungszentrum, Garching, Germany','Ingolstadt, Germany',48.264671,11.671391,48.7665351,11.4257541,'16:00',45,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (18,8,'pattern','Garching, Forschungszentrum, Garching, Germany','Ingolstadt, Germany',48.264671,11.671391,48.7665351,11.4257541,'18:00',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (19,8,'pattern','Garching, Forschungszentrum, Garching, Germany','Ingolstadt, Germany',48.264671,11.671391,48.7665351,11.4257541,'10:00',15,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (20,8,'pattern','Ingolstadt, Germany','Garching, Forschungszentrum, Garching, Germany',48.7665351,11.4257541,48.264671,11.671391,'16:30',60,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (21,8,'pattern','Ingolstadt, Germany','Garching, Forschungszentrum, Garching, Germany',48.7665351,11.4257541,48.264671,11.671391,'17:30',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (22,8,'pattern','Ingolstadt, Germany','Garching, Forschungszentrum, Garching, Germany',48.7665351,11.4257541,48.264671,11.671391,'11:00',30,'',0.0,'Daily','Yes');

INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (23,8,'subscription','Sendlinger Tor, Munich, Germany','Garching-Hochbr?ck, Hochbr?ck, Germany',48.133445,11.566683,48.2473897,11.6309592,'13:00',90,'2015-07-14',NULL,'','Yes');



INSERT INTO `Matching` (matchingId,userId1,userId2,routePatternId1,routePatternId2,active,value) VALUES (5,8,7,9,7,1,0.989061162834593);
INSERT INTO `Matching` (matchingId,userId1,userId2,routePatternId1,routePatternId2,active,value) VALUES (6,8,7,10,7,0,0.989061162834593);
INSERT INTO `Matching` (matchingId,userId1,userId2,routePatternId1,routePatternId2,active,value) VALUES (7,8,7,11,8,0,0.989061162834593);

INSERT INTO `Route` (routeId,matchingId,routePatternId,date,time,done,status) VALUES (1,5,9,'2015-07-16','08:00',1,'wait');
INSERT INTO `Route` (routeId,matchingId,routePatternId,date,time,done,status) VALUES (2,5,9,'2015-07-17','08:00',1,'wait');
INSERT INTO `Route` (routeId,matchingId,routePatternId,date,time,done,status) VALUES (3,5,9,'2015-07-18','08:00',1,'wait');
INSERT INTO `Route` (routeId,matchingId,routePatternId,date,time,done,status) VALUES (4,5,9,'2015-07-19','08:00',0,'wait');
INSERT INTO `Route` (routeId,matchingId,routePatternId,date,time,done,status) VALUES (5,5,9,'2015-07-20','08:00',0,'wait');




INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (1,'Ramersdorf-Perlach','München','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (2,'','Munich','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (3,'','Munich','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (4,'Ramersdorf-Perlach','München','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (5,'','Garching','Altstadt-Lehel','München');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (6,'','Garching','Sendling-Westpark','München');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (7,'','Garching','Ramersdorf-Perlach','Munich');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (8,'','Garching','Hochbrück','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (9,'Ramersdorf-Perlach','München','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (10,'Altstadt-Lehel','München','Hochbrück','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (11,'Altstadt-Lehel','München','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (12,'Altstadt-Lehel','München','','Ingolstadt');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (13,'','Ingolstadt','','Munich');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (14,'','Ingolstadt','','Munich');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (15,'Altstadt-Lehel','München','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (16,'Altstadt-Lehel','München','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (17,'Ramersdorf-Perlach','München','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (18,'Altstadt-Lehel','München','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (19,'Altstadt-Lehel','München','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (20,'Altstadt-Lehel','München','','Garching');
INSERT INTO `Search` (searchId,startAreaSubLoc,startAreaLoc,endAreaSubLoc,endAreaLoc) VALUES (21,'Altstadt-Lehel','München','Hochbrück','Garching');

# --- !Downs

DELETE TABLE `User`
DELETE TABLE `RoutePattern`
DELETE TABLE `Matching`
DELETE TABLE `Search`