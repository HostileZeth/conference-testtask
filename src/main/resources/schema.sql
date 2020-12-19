CREATE TABLE task (
  id          BIGINT PRIMARY KEY,
  description VARCHAR(64) NOT NULL,
  completed   BIT NOT NULL);
  
CREATE TABLE room (
	id		BIGINT AUTO_INCREMENT PRIMARY KEY,
	room_name VARCHAR(15) NOT NULL,
	description VARCHAR(64));
	
CREATE TABLE users (
  username VARCHAR(60) PRIMARY KEY,
  password VARCHAR(60) NOT NULL,
  displaying_name VARCHAR(60) NOT NULL,
  role ENUM('LISTENER', 'PRESENTER', 'ADMIN') NOT NULL);
  
CREATE TABLE presentation (
  id          BIGINT AUTO_INCREMENT PRIMARY KEY,
  title			VARCHAR(64) NOT NULL);
  
CREATE TABLE schedule (
  presentation_id BIGINT PRIMARY KEY,
  room_id BIGINT NOT NULL,
  presentation_begin DATETIME NOT NULL,
  presentation_end DATETIME NOT NULL,
  constraint fk_schedule_room foreign key(room_id) references room(id),
  constraint fk_schedule_presentation foreign key(presentation_id) references presentation(id)
  );

CREATE TABLE user_presentation (
  user_id VARCHAR(60) NOT NULL,
  presentation_id BIGINT NOT NULL,
  constraint fk_up_user foreign key(user_id) references users(username),
  constraint fk_up_presentation foreign key(presentation_id) references presentation(id),
  constraint pk_user_pres primary key(user_id, presentation_id)
  );
  



	
-- security stuff -- duplicate data for security tables and general stuff ? -- or VIEW ?
/*create table users(
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(50) not null,
    enabled boolean not null);

create table authorities (
    username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username));
    create unique index ix_auth_username on authorities (username,authority);

-- security stuff v2 
create table groups (
  id bigint generated by default as identity(start with 0) primary key,
  group_name varchar_ignorecase(50) not null);

create table group_authorities (
  group_id bigint not null,
  authority varchar(50) not null,
  constraint fk_group_authorities_group foreign key(group_id) references groups(id));

create table group_members (
  id bigint generated by default as identity(start with 0) primary key,
  username varchar(50) not null,
  group_id bigint not null,
  constraint fk_group_members_group foreign key(group_id) references groups(id));*/