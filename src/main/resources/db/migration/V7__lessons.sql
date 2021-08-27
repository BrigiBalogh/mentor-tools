create table lessons (id bigint not null auto_increment,
                      title varchar(255) not null,
                      url varchar(255) not null,
                      module_id bigint not null,
                      primary key (id),
                      foreign key (module_id) references modules (id));