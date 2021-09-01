create table registrations (
    student_id bigint not null,
    training_class_id bigint not null,
    status varchar(255),
    primary key ( student_id, training_class_id),
    constraint fk_training_class foreign key (training_class_id) references training_classes(id),
    constraint fk_student foreign key (student_id) references students(id));

