# --- !Ups
INSERT INTO `User` VALUES (1,'user1','Thomas','Spahn','Public','42ef13e75a5c91d97f2caba697b9028c7d6bb523','thomas@hotmail.com',0.0);
INSERT INTO `User` VALUES (2,'user2','Yannis','Stratakos','Public','42ef13e75a5c91d97f2caba697b9028c7d6bb523','yannis@hotmail.com',0.0);
INSERT INTO `User` VALUES (3,'user3','Fabian','Kolbe','Public','42ef13e75a5c91d97f2caba697b9028c7d6bb523','fabian@hotmail.com',0.0);

INSERT INTO `RoutePattern` VALUES (1,1,'pattern','Garching, Forschungszentrum, Garching, Germany','AUDI AG, Ingolstadt',48.264671,11.671391,48.7665351,11.4257541,'10:00',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` VALUES (2,1,'pattern','AUDI AG, Ingolstadt','Garching, Forschungszentrum, Garching, Germany',48.7665351,11.4257541,48.264671,11.671391,'18:30',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` VALUES (3,2,'pattern','Garching, Germany','Ingolstadt, Germany',48.2488721,11.6532477,48.7665351,11.4257541,'10:20',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` VALUES (4,2,'pattern','Ingolstadt, Germany','Garching, Germany',48.7665351,11.4257541,48.2488721,11.6532477,'18:20',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` VALUES (5,3,'pattern','Carl-Zeiss-Ring 6-8, 85737, Ismaning, Germany','Ingolstadt, Germany',48.23532,11.68019,48.7665351,11.4257541,'10:30',30,'',0.0,'Daily','Yes');
INSERT INTO `RoutePattern` VALUES (6,3,'pattern','Ingolstadt, Germany','Carl-Zeiss-Ring 6-8, 85737, Ismaning, Germany',48.7665351,11.4257541,48.23532,11.68019,'18:15',30,'',0.0,'Daily','Yes');

INSERT INTO `Matching` VALUES (1,2,1,3,1,1,0.98481663199518);
INSERT INTO `Matching` VALUES (2,2,1,4,2,1,0.98481663199518);
INSERT INTO `Matching` VALUES (3,3,1,5,1,0,0.999665358185293);
INSERT INTO `Matching` VALUES (4,3,2,5,3,0,0.992312289775114);
INSERT INTO `Matching` VALUES (5,3,1,6,2,0,0.999665358185293);
INSERT INTO `Matching` VALUES (6,3,2,6,4,0,0.992312289775114);

INSERT INTO `Route` VALUES (1,1,3,'2015-07-20','10:20','wait');
INSERT INTO `Route` VALUES (2,2,4,'2015-07-20','18:15','wait');
INSERT INTO `Route` VALUES (3,1,3,'2015-07-21','10:20','wait');
INSERT INTO `Route` VALUES (4,2,4,'2015-07-21','18:15','wait');

INSERT INTO `Route` VALUES (5,1,3,'2015-07-22','10:20','wait');
INSERT INTO `Route` VALUES (6,2,4,'2015-07-22','18:15','wait');
INSERT INTO `Route` VALUES (7,1,3,'2015-07-23','10:20','wait');
INSERT INTO `Route` VALUES (8,2,4,'2015-07-23','18:15','wait');

INSERT INTO `Search` VALUES (1,'Ramersdorf-Perlach','M�nchen','','Garching');
INSERT INTO `Search` VALUES (2,'','Munich','','Garching');
INSERT INTO `Search` VALUES (3,'','Munich','','Garching');
INSERT INTO `Search` VALUES (4,'Ramersdorf-Perlach','M�nchen','','Garching');
INSERT INTO `Search` VALUES (5,'','Garching','Altstadt-Lehel','M�nchen');
INSERT INTO `Search` VALUES (6,'','Garching','Sendling-Westpark','M�nchen');
INSERT INTO `Search` VALUES (7,'','Garching','Ramersdorf-Perlach','Munich');
INSERT INTO `Search` VALUES (8,'','Garching','Hochbr�ck','Garching');
INSERT INTO `Search` VALUES (9,'Ramersdorf-Perlach','M�nchen','','Garching');
INSERT INTO `Search` VALUES (10,'Altstadt-Lehel','M�nchen','Hochbr�ck','Garching');
INSERT INTO `Search` VALUES (11,'Altstadt-Lehel','M�nchen','','Garching');
INSERT INTO `Search` VALUES (12,'Altstadt-Lehel','M�nchen','','Ingolstadt');
INSERT INTO `Search` VALUES (13,'','Ingolstadt','','Munich');
INSERT INTO `Search` VALUES (14,'','Ingolstadt','','Munich');
INSERT INTO `Search` VALUES (15,'Altstadt-Lehel','M�nchen','','Garching');
INSERT INTO `Search` VALUES (16,'Altstadt-Lehel','M�nchen','','Garching');
INSERT INTO `Search` VALUES (17,'Ramersdorf-Perlach','M�nchen','','Garching');
INSERT INTO `Search` VALUES (18,'Altstadt-Lehel','M�nchen','','Garching');
INSERT INTO `Search` VALUES (19,'Altstadt-Lehel','M�nchen','','Garching');
INSERT INTO `Search` VALUES (20,'Altstadt-Lehel','M�nchen','','Garching');
INSERT INTO `Search` VALUES (21,'Altstadt-Lehel','M�nchen','Hochbr�ck','Garching');

# --- !Downs
DELETE FROM `User`;
DELETE FROM `RoutePattern`;
DELETE FROM `Matching`;
DELETE FROM `Search`;