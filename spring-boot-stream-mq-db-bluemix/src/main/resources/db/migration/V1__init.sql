CREATE TABLE ingest_data (
	id BIGINT NOT NULL AUTO_INCREMENT,
	file_path varchar(255) NOT NULL,
	value varchar(255) NOT NULL,
	PRIMARY KEY (id)
);
