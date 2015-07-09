drop table if exists tweagle.new_synonyms;
create table tweagle.new_synonyms (
id int auto_increment,
word varchar(35),
synonyms varchar(60),
primary key (id)
);