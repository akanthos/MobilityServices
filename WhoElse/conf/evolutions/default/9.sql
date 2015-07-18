# --- !Ups
CREATE TABLE `Notification` (
	`notificationId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`from_userId`	INTEGER,
	`to_userId`	INTEGER,
	`nType`	NUMERIC,
	`message`	BLOB,
	`patternId1`	INTEGER,
	`patternId2`	INTEGER,
	`seen`	INTEGER,
	`answered`	INTEGER
);

# --- !Downs
DROP TABLE `Notification`