# --- !Ups

CREATE TABLE `Rating` (
	`transaction_id`	INTEGER,
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`rating`	INTEGER,
	`comment`	TEXT,
	`role`	TEXT,
	`rating_date`	INTEGER
);

# --- !Downs

DROP TABLE Rating