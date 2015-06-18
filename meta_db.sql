create table datamart(mart_id int not null auto_increment, mart_name varchar(100) not null, constraint pk_datamart primary key(mart_id));
create table dimensions(dim_id int not null auto_increment, dim_name varchar(100) not null, mart_id int, constraint pk_dim primary key(dim_id), constraint fk_dim foreign key(mart_id) references datamart(mart_id));
create table facts(fact_id int not null auto_increment, fact_name varchar(100) not null,mart_id int not null, constraint pk_fact primary key(fact_id), constraint fk_fact foreign key(mart_id) references datamart(mart_id));








