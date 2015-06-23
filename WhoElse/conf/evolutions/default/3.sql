# --- !Ups
CREATE TABLE `Route` (
	`routeId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`userId`	INTEGER,
	`routePatternId`	INTEGER,
	`startAddress`	TEXT,
	`endAddress`	TEXT,
	`startLat`	REAL,
	`startLong`	REAL,
	`endLat`	REAL,
	`endLong`	REAL,
	`dateTime`	TEXT
);

# --- !Downs
DROP TABLE `Route`
