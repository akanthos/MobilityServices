# --- !Ups

INSERT INTO `User` (userId,username,firstName,lastName,company,password,email,balance) VALUES (1,'user1','Thomas','Spahn','Public','42ef13e75a5c91d97f2caba697b9028c7d6bb523','thomas@hotmail.com',0.0);
INSERT INTO `User` (userId,username,firstName,lastName,company,password,email,balance) VALUES (2,'user2','Yannis','Stratakos','Public','42ef13e75a5c91d97f2caba697b9028c7d6bb523','yannis@hotmail.com',0.0);
INSERT INTO `User` (userId,username,firstName,lastName,company,password,email,balance) VALUES (3,'user3','Fabian','Kolbe','Public','42ef13e75a5c91d97f2caba697b9028c7d6bb523','fabian@hotmail.com',0.0);


INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (1,1,'pattern','Garching, Forschungszentrum, Garching, Germany','AUDI AG, Ingolstadt',48.264671,11.671391,48.7665351,11.4257541,'10:00',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (2,1,'pattern','AUDI AG, Ingolstadt','Garching, Forschungszentrum, Garching, Germany',48.7665351,11.4257541,48.264671,11.671391,'18:30',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (3,2,'pattern','Garching, Germany','Ingolstadt, Germany',48.2488721,11.6532477,48.7665351,11.4257541,'10:20',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (4,2,'pattern','Ingolstadt, Germany','Garching, Germany',48.7665351,11.4257541,48.2488721,11.6532477,'18:15',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (5,3,'pattern','Carl-Zeiss-Ring 6-8, 85737, Ismaning, Germany','Ingolstadt, Germany',48.23532,11.68019,48.7665351,11.4257541,'10:30',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` (routePatternId,userId,request_type,startAddress,endAddress,startLat,startLong,endLat,endLong,time,flexibility,date,punctuality,periodicity,car) VALUES (6,3,'pattern','Ingolstadt, Germany','Carl-Zeiss-Ring 6-8, 85737, Ismaning, Germany',48.7665351,11.4257541,48.23532,11.68019,'18:15',30,'',0.0,'Daily','Yes');

INSERT INTO `Matching` (matchingId,userId1,userId2,routePatternId1,routePatternId2,active,value) VALUES (1,2,1,3,1,1,0.98481663199518);
INSERT INTO `Matching` (matchingId,userId1,userId2,routePatternId1,routePatternId2,active,value) VALUES (2,2,1,4,2,1,0.98481663199518);
INSERT INTO `Matching` (matchingId,userId1,userId2,routePatternId1,routePatternId2,active,value) VALUES (3,3,1,5,1,0,0.999665358185293);
INSERT INTO `Matching` (matchingId,userId1,userId2,routePatternId1,routePatternId2,active,value) VALUES (4,3,2,5,3,0,0.992312289775114);
INSERT INTO `Matching` (matchingId,userId1,userId2,routePatternId1,routePatternId2,active,value) VALUES (5,3,1,6,2,0,0.999665358185293);
INSERT INTO `Matching` (matchingId,userId1,userId2,routePatternId1,routePatternId2,active,value) VALUES (6,3,2,6,4,0,0.992312289775114);


INSERT INTO `Route` (routeId,matchingId,routePatternId,date,time,status) VALUES (1,1,3,'2015-07-20','10:20','wait');
INSERT INTO `Route` (routeId,matchingId,routePatternId,date,time,status) VALUES (2,2,4,'2015-07-20','18:15','wait');
INSERT INTO `Route` (routeId,matchingId,routePatternId,date,time,status) VALUES (3,1,3,'2015-07-21','10:20','wait');
INSERT INTO `Route` (routeId,matchingId,routePatternId,date,time,status) VALUES (4,2,4,'2015-07-21','18:15','wait');

INSERT INTO `Route` (routeId,matchingId,routePatternId,date,time,status) VALUES (5,1,3,'2015-07-22','10:20','wait');
INSERT INTO `Route` (routeId,matchingId,routePatternId,date,time,status) VALUES (6,2,4,'2015-07-22','18:15','wait');
INSERT INTO `Route` (routeId,matchingId,routePatternId,date,time,status) VALUES (7,1,3,'2015-07-23','10:20','wait');
INSERT INTO `Route` (routeId,matchingId,routePatternId,date,time,status) VALUES (8,2,4,'2015-07-23','18:15','wait');





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