create table registrations (registration_id bigint not null auto_increment,
                            status varchar(255),
                            student_id bigint not null,
                            training_class_id bigint not null,
                            primary key (registration_id),
                            foreign key (student_id) references students(id),
                            foreign key (training_class_id) references training_classes(id));

