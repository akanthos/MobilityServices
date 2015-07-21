# --- !Ups
CREATE TABLE `Route` (
	`routeId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`matchingId`	INTEGER,
	`routePatternId`	INTEGER,
	`date`	TEXT,
	`time`	TEXT,
	`status`	TEXT
);

# --- !Downs
DROP TABLE `Route`
