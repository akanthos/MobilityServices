# --- !Ups
CREATE TABLE `Notification` (
	`notificationId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`userId`	INTEGER,
	`nType`	TEXT,
	`message`	TEXT,
	`seen`	INTEGER,
	`answered`	INTEGER
);

# --- !Downs
DROP TABLE `Notification`