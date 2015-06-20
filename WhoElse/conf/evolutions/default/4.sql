# --- !Ups
CREATE TABLE `RoutePoints` (
	`pointId`	INTEGER,
	`routeId`	INTEGER,
	`lat`	REAL,
	`lng`	REAL,
	`dateTime`	TEXT,
	PRIMARY KEY(pointId)
);

# --- !Downs
DROP TABLE `RoutePoints`