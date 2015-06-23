# --- !Ups
CREATE TABLE `Matching` (
	`matchingId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`userId`	INTEGER,
	`routePatternId1`	INTEGER,
	`routePatternId2`	INTEGER,
	`value`	REAL
);

# --- !Downs
DROP TABLE `Matching`