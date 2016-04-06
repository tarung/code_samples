CREATE TABLE db_connection(
	id INT NOT NULL auto_increment,
	driverClassName VARCHAR(100) NOT NULL,
	url VARCHAR(200) NOT NULL,
	userName VARCHAR(50) NOT NULL,
	password VARCHAR(50) NOT NULL,
	primary key (id)
);
CREATE TABLE sap_system(
	id INT NOT NULL auto_increment,
	destination_name VARCHAR(50) NOT NULL,
	host_name VARCHAR(100) NOT NULL,
	sys_nr VARCHAR(50) NOT NULL,
	user_name VARCHAR(50) NOT NULL,
	password VARCHAR(50) NOT NULL,
	language_code VARCHAR(2) NOT NULL,
	is_pooled BIT NOT NULL,
	pool_capacity INT,
	peak_limit INT ,
	primary key (id)
);
CREATE TABLE profile_table_field(
	id INT NOT NULL auto_increment,
	field_name VARCHAR(50) NOT NULL,
	field_type VARCHAR(50) NOT NULL,
	table_id INT NOT NULL,
	primary key (id)
);
CREATE TABLE profile_table_field_filter(
	id INTEGER NOT NULL auto_increment,
	field_id INT NOT NULL,
	primary key (id)
);
CREATE TABLE extraction_profile(
	id INT NOT NULL auto_increment,
	profile_name VARCHAR(50) NOT NULL,
	profile_description VARCHAR(256) NOT NULL,
	primary key (id)
);
CREATE TABLE profile_table(
	id INT NOT NULL auto_increment,
	table_name VARCHAR(50) NOT NULL,
	profile_id INT NOT NULL,
	primary key (id)
);
CREATE TABLE extraction_task(
	id INT NOT NULL auto_increment,
	profile_name VARCHAR(50) NOT NULL,
	task_description TEXT NOT NULL,
	status VARCHAR(25) NOT NULL,
	percentcompletion INT NOT NULL,
	startedOn DATETIME NOT NULL,
	completedOn DATETIME,
	primary key (id)
);
CREATE TABLE extraction_log(
	id INT NOT NULL auto_increment,
	task_id INT NOT NULL,
	table_name VARCHAR(10),
	log_time DATETIME NOT NULL,
	message TEXT NOT NULL,
	is_error BIT NOT NULL default 0,
	primary key (id)
);
CREATE TABLE scheduled_task(
	id INT NOT NULL auto_increment,
	name VARCHAR(10) NOT NULL,
	profile_id INT NOT NULL, 
	start_date DATETIME NOT NULL,
	repeat_after BIGINT,
	last_execution_time DATETIME,
	next_execution_time DATETIME,	
	primary key (id)
);

ALTER TABLE extraction_profile	ADD db_connection_id INT NOT NULL ;
ALTER TABLE extraction_profile	ADD sap_system_id INT NOT NULL ;
ALTER TABLE extraction_profile	ADD continue_on_failure BIT NOT NULL default 1;
ALTER TABLE extraction_profile	ADD write_batch_size INT NOT NULL default 5000;
ALTER TABLE extraction_profile	ADD if_table_exists VARCHAR(25) NOT NULL default 'DROP';
ALTER TABLE db_connection	ADD name VARCHAR(50) NOT NULL default '';
ALTER TABLE db_connection	ADD description VARCHAR(250);
ALTER TABLE sap_system		ADD description VARCHAR(250);
ALTER TABLE scheduled_task	ADD end_date DATETIME;
ALTER TABLE extraction_task	ADD scheduler_id INT;
ALTER TABLE sap_system	ADD client_number INT;

ALTER TABLE profile_table_field ADD field_length INT;
ALTER TABLE profile_table_field ADD field_decimal INT;
ALTER TABLE profile_table_field ADD position INT NOT NULL;

ALTER TABLE profile_table_field_filter ADD operator VARCHAR(50) NOT NULL default '=';
ALTER TABLE profile_table_field_filter ADD criteria VARCHAR(50) NOT NULL default '';
ALTER TABLE profile_table_field_filter ADD criteria2 VARCHAR(50);
ALTER TABLE profile_table_field_filter ADD joinby VARCHAR(50);
ALTER TABLE profile_table ADD description VARCHAR(250);

ALTER TABLE extraction_log ADD FOREIGN KEY (task_id) REFERENCES extraction_task (id);
ALTER TABLE profile_table_field ADD FOREIGN KEY (table_id) REFERENCES profile_table (id);
ALTER TABLE profile_table_field_filter ADD FOREIGN KEY (field_id) REFERENCES profile_table_field (id);
ALTER TABLE profile_table ADD FOREIGN KEY (profile_id) REFERENCES extraction_profile (id);
ALTER TABLE extraction_profile ADD FOREIGN KEY (db_connection_id) REFERENCES db_connection (id);
ALTER TABLE extraction_profile ADD FOREIGN KEY (sap_system_id) REFERENCES sap_system (id);
