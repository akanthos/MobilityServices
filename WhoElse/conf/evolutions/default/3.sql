# --- !Ups

CREATE TABLE `Transaction` (
	`borrower_id`	INTEGER,
	`date_ordered`	TEXT,
	`date_begin`	INTEGER,
	`date_end`	INTEGER,
	`insurance`	INTEGER,
	`cleaning_level`	TEXT,
	`rating_id`	INTEGER
);

# --- !Downs

DROP TABLE Transaction
