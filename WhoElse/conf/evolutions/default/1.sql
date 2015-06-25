# --- !Ups
CREATE TABLE `User` (
	`userId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`username`	TEXT UNIQUE,
	`firstName`	TEXT,
	`lastName`	TEXT,
	`company`	TEXT,
	`password`	TEXT,
	`email`	TEXT,
	`balance`	REAL
);

# --- !Downs
DROP TABLE `User`