# --- !Ups
CREATE TABLE `Ratings` (
	`ratingId`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`userId`	INTEGER,
	`userIdPost`	INTEGER,
	`rating`	INTEGER,
	`comment`	TEXT,
	`ratingDate`	TEXT
);

# --- !Downs
DROP TABLE `Ratings`