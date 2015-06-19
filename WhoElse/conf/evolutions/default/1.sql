# --- !Ups
CREATE TABLE `Item` (
	`id`	INTEGER PRIMARY KEY AUTOINCREMENT,
	`user_id`	INTEGER,
	`description`	TEXT,
	`dress_size`	INTEGER,
	`color`	TEXT,
	`price_per_day`	NUMERIC,
	`material`	TEXT,
	`brand`	TEXT,
	`dress_type`	TEXT
);

# --- !Downs
DROP TABLE Item