# --- !Ups
CREATE TABLE `RoutePatterns` (
	`routePatternId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`userId`	INTEGER,
	`startAddress`	TEXT,
	`endAddress`	TEXT,
	`startLat`	REAL,
	`startLong`	REAL,
	`endLat`	REAL,
	`endLong`	REAL,
	`dateTime`	TEXT,
	`punctuality`	REAL,
	`periodicity`	TEXT
);

# --- !Downs
DROP TABLE `RoutePatterns`