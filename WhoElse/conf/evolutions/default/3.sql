# --- !Ups
CREATE TABLE `Route` (
	`routeId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`userId`	INTEGER,
	`routePatternId`	INTEGER,
	`date`	TEXT,
	`time`	TEXT,
	`done`	INTEGER
);

# --- !Downs
DROP TABLE `Route`
