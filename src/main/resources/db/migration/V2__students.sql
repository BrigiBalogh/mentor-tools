create table students (id bigint not null auto_increment,
                       name varchar(255) not null,
                       email varchar(255) not null,
                       github_username varchar(255),
                       comment varchar(1000)
                       primary key (id));