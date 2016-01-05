create table game;
use game;

create table user(
	id int primary key auto_increment,
	name varchar(50),
	password varchar(50),
	score int,
	sex int,
	win int,
	lost int
);