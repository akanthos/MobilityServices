# --- !Ups
CREATE TABLE `User` (
	`userId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`username`	TEXT UNIQUE,
	`firstName`	TEXT,
	`lastName`	TEXT,
	`password`	TEXT,
	`email`	TEXT,
	`token`	TEXT,
	`balance`	REAL
);

# --- !Downs
DROP TABLE `User`