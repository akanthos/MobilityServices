# --- !Ups
CREATE TABLE `RoutePattern` (
	`routePatternId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`userId`	INTEGER,
	`startAddress`	TEXT,
	`endAddress`	TEXT,
	`startLat`	REAL,
	`startLong`	REAL,
	`endLat`	REAL,
	`endLong`	REAL,
	`time`	TEXT,
	`date`	TEXT,
	`punctuality`	REAL,
	`periodicity`	TEXT,
	`car`	TEXT
);

# --- !Downs
DROP TABLE `RoutePattern`