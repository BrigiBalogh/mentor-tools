create table lessons_completions (
    student_id bigint not null,
    lesson_id bigint not null,
    task_status varchar(255) not null,
    task_date date,
    commit_url varchar(255),
    video_status varchar(255) not null,
    video_date date,
    primary key (student_id, lesson_id));


  alter table lessons_completions
    add CONSTRAINT fk_LessonsLeComp foreign key (lesson_id) references lessons(id);
alter table lessons_completions
    add CONSTRAINT fk_StudentLeComp foreign key (student_id) references students(id);

