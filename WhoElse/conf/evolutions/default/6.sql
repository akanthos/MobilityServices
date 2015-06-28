# --- !Ups
CREATE TABLE `Matching` (
	`matchingId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`userId1`	INTEGER,
	`userId2`	INTEGER,
	`routePatternId1`	INTEGER,
	`routePatternId2`	INTEGER,
	`value`	REAL
);

# --- !Downs
DROP TABLE `Matching`