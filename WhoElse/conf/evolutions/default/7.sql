# --- !Ups

INSERT INTO `User` (userId,username,firstName,lastName,company,password,email,balance) VALUES (7,'user1','Thomas','Spahn','Public','42ef13e75a5c91d97f2caba697b9028c7d6bb523','thomas@hotmail.com',0.0);
INSERT INTO `User` (userId,username,firstName,lastName,company,password,email,balance) VALUES (8,'user2','Yannis','Stratakos','Public','42ef13e75a5c91d97f2caba697b9028c7d6bb523','yannis@hotmail.com',0.0);

INSERT INTO `RoutePattern` (routePatternId,userId,startAddress,endAddress,startLat,startLong,endLat,endLong,time,date,punctuality,periodicity) VALUES (7,7,'Marienplatz, Munich, Germany','Garching, Germany',48.137079,11.5760064,48.2488721,11.6532477,'08:00','',0.0,'Daily');
INSERT INTO `RoutePattern` (routePatternId,userId,startAddress,endAddress,startLat,startLong,endLat,endLong,time,date,punctuality,periodicity) VALUES (8,7,'Garching, Germany','Marienplatz, Munich, Germany',48.2488721,11.6532477,48.137079,11.5760064,'17:00','',0.0,'Daily');
INSERT INTO `RoutePattern` (routePatternId,userId,startAddress,endAddress,startLat,startLong,endLat,endLong,time,date,punctuality,periodicity) VALUES (9,8,'Sendlinger-Tor-Platz, Munich, Germany','Boltzmannstraﬂe, Garching, Germany',48.1337196,11.5666065,48.264838,11.6712126,'08:00','',0.0,'Daily');
INSERT INTO `RoutePattern` (routePatternId,userId,startAddress,endAddress,startLat,startLong,endLat,endLong,time,date,punctuality,periodicity) VALUES (10,8,'Boltzmannstraﬂe, Garching, Germany','Sendlinger-Tor-Platz, Munich, Germany',48.264838,11.6712126,48.1337196,11.5666065,'17:00','',0.0,'Daily');

INSERT INTO `Matching` (matchingId,userId1,userId2,routePatternId1,routePatternId2,value) VALUES (5,8,7,9,7,0.989061162834593);
INSERT INTO `Matching` (matchingId,userId1,userId2,routePatternId1,routePatternId2,value) VALUES (6,8,7,10,8,0.989061162834593);

# --- !Downs

DELETE TABLE `User`
DELETE TABLE `RoutePattern`
DELETE TABLE `Matching`