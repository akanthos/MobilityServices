# --- !Ups
CREATE TABLE `Search` (
	`searchId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`startAreaSubLoc`	TEXT,
	`startAreaLoc`	TEXT,
	`endAreaSubLoc`	TEXT,
	`endAreaLoc`	TEXT
);

# --- !Downs
DROP TABLE `Search`

