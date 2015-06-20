# --- !Ups
CREATE TABLE `Matchings` (
	`matchingId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`userId`	INTEGER,
	`routePatternId1`	INTEGER,
	`routePatternId2`	INTEGER,
	`value`	REAL
);

# --- !Downs
DELETE TABLE `Matchings`