# --- !Ups
CREATE TABLE `Notification` (
	`notificationId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`from_userId`	INTEGER,
	`to_userId`	INTEGER,
	`nType`	TEXT,
	`message`	TEXT,
	`seen`	INTEGER,
	`answered`	INTEGER
);

# --- !Downs
DROP TABLE `Notification`