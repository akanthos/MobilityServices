# --- !Ups
CREATE TABLE `RoutePoint` (
	`pointId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`routeId`	INTEGER,
	`lat`	REAL,
	`lng`	REAL,
	`dateTime`	TEXT
);

# --- !Downs
DROP TABLE `RoutePoint`