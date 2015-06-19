# --- !Ups

CREATE TABLE `User` (
	`firstname`	TEXT,
	`lastname`	TEXT,
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`email`	TEXT,
	`date_of_birth`	TEXT,
	`password_hash`	INTEGER
);

# --- !Downs
DROP TABLE User