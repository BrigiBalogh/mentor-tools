package training360.mentortools.trainingClasses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import training360.mentortools.lessoncompletion.*;
import training360.mentortools.lessons.CreateLessonCommand;
import training360.mentortools.lessons.LessonDto;
import training360.mentortools.modules.CreateModuleCommand;
import training360.mentortools.modules.ModuleDto;
import training360.mentortools.students.CreateStudentCommand;
import training360.mentortools.students.StudentDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(statements = {"alter table lessons_completions drop foreign key if exists fk_StudentLeComp",
        "alter table lessons_completions drop foreign key if exists fk_LessonsLeComp",
        "delete from students", "delete from lessons_completions"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LessonCompletionControllerRestTemplateIT {



    @Autowired
    TestRestTemplate template;

    StudentDto student;
    LessonDto lesson;
    LessonCompletionDto lessonCompletion;


    static String URL_MODULE = "/api/modules";
    static String URL_STUDENT = "/api/students";
    static String URL_LESSON = "/api/modules/{module-id}/lessons";
    static String URL_LESSON_COMPLETION = "/api/students/{id}/lessoncompletions";
    static String URL_LESSON_COMPLETION_ID = "/api/students/{id}/lessoncompletions/{lesson-id}";

    static Map<String, String> params = new HashMap<>();

    @BeforeEach
    void init() {
      student = template.postForObject(URL_STUDENT, new CreateStudentCommand(
                        "Kiss István", "kiss.istvan@gmail.com"
                        , "Kiss.Istvan", "lemaradt"),
                StudentDto.class);
        ModuleDto module = template.postForObject(URL_MODULE, new CreateModuleCommand("JPA", "/modules"),
                ModuleDto.class);
        params.put("module-id", module.getId().toString());
        lesson = template.postForObject(
                URL_LESSON,
                new CreateLessonCommand(
                        "JPA bevezetes",
                        "/modules/jpa-bevezetes"),
                LessonDto.class, params);
        params.put("id", student.getId().toString());
        lessonCompletion = template.postForObject(URL_LESSON_COMPLETION,
                new CreateLessonCompletionCommand(lesson.getId(),
                        new Video(VideoStatus.COMPLETED,
                                LocalDate.of(2021, 7, 15)),
                        new Task(TaskStatus.COMPLETED,
                                LocalDate.of(2021, 7, 15),
                                "gsvm1115")), LessonCompletionDto.class, params);


      /*  student1 = template.postForObject(URL_STUDENT, new CreateStudentCommand(
                        "Kiss Zsuzsanna", "kiss.zsuzsa@gmail.com"
                        , "Kiss.Zsuzsa", "halad"),
                StudentDto.class);
        module1 = template.postForObject(URL_MODULE, new CreateModuleCommand("Git", "/git"),
                ModuleDto.class);
        params.put("module-id", module1.getId().toString());
        lesson1 = template.postForObject(URL_LESSON,
                new CreateLessonCommand(
                        "git bevezetes",
                        "/modules/git-bevezetes"), LessonDto.class, params);
        params.put("id", student1.getId().toString());
        lessonCompletion1 = template.postForObject(URL_LESSON_COMPLETION,
                new CreateLessonCompletionCommand(lesson1.getId(),
                        new Video(VideoStatus.NOT_COMPLETED, null),
                        new Task(TaskStatus.NOT_COMPLETED, null,
                                null)), LessonCompletionDto.class, params);*/
    }


    @Test
    void testGetLessonsWithCompletions() {

        LessonDto lesson1 = template.postForObject(URL_LESSON,
                new CreateLessonCommand(
                        "git bevezetes",
                        "/modules/git-bevezetes"), LessonDto.class, params);
        LessonCompletionDto lessonCompletion1 = template.postForObject(URL_LESSON_COMPLETION,
                new CreateLessonCompletionCommand(lesson1.getId(),
                        new Video(VideoStatus.NOT_COMPLETED, null),
                        new Task(TaskStatus.NOT_COMPLETED, null,
                                null)), LessonCompletionDto.class, params);


        List<LessonCompletionDto> result = template.exchange(URL_LESSON_COMPLETION,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LessonCompletionDto>>() {
                }, params)
                .getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(LessonCompletionDto::getLesson)
                .extracting(LessonDto::getTitle)
                .containsExactly("JPA bevezetes", "git bevezetes");
    }


    @Test
    void findById() {
        params.put("lesson-id", lesson.getId().toString());
        LessonCompletionDto result = template
                .getForObject(URL_LESSON_COMPLETION_ID, LessonCompletionDto.class, params);

        assertEquals(VideoStatus.COMPLETED, result.getVideo().getVideoStatus());
        assertEquals(TaskStatus.COMPLETED, result.getTask().getTaskStatus());
        assertEquals(lesson, result.getLesson());
    }



    @Test
    void testCreate() {

        assertEquals(VideoStatus.COMPLETED, lessonCompletion.getVideo().getVideoStatus());
        assertEquals(TaskStatus.COMPLETED, lessonCompletion.getTask().getTaskStatus());
        assertEquals(lesson, lessonCompletion.getLesson());

    }


    @Test
    void testUpdate() {

        LessonCompletionDto result = template.exchange(
                URL_LESSON_COMPLETION,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateLessonCompletionCommand(lesson.getId(),
                        new Video(VideoStatus.COMPLETED,
                                LocalDate.of(2021, 7, 17)),
                        new Task(TaskStatus.COMPLETED,
                                LocalDate.of(2021, 7, 17),
                                "gsvm11155"))), LessonCompletionDto.class, params).getBody();

        assertEquals(VideoStatus.COMPLETED, result.getVideo().getVideoStatus());
        assertEquals(TaskStatus.COMPLETED, result.getTask().getTaskStatus());
        assertEquals(lesson, result.getLesson());
    }


    @Test
    void testDeleteById() {

        params.put("lessons-id", lesson.getId().toString());
        template.delete(URL_LESSON_COMPLETION_ID, params);


        List<LessonCompletionDto> result = template.exchange(URL_LESSON_COMPLETION,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LessonCompletionDto>>() {
                }, params)
                .getBody();

        assertThat(result).
                hasSize(1);

    }

    @Test
    void testInvalidVideoStatus() {
        Problem problem = template.postForObject(
                URL_LESSON_COMPLETION,
                new CreateLessonCompletionCommand(lesson.getId(),
                        new Video(null, null),
                        new Task(TaskStatus.COMPLETED,
                                LocalDate.of(2021, 7, 15),
                                "gsvm1115")), Problem.class, params);

        assertEquals(Status.INTERNAL_SERVER_ERROR, problem.getStatus());
    }


    @Test
    void TestInvalidPost() {

        Problem problem = template.postForObject(
                URL_LESSON_COMPLETION,
                new CreateLessonCompletionCommand(-1L,
                        new Video(VideoStatus.COMPLETED,
                                LocalDate.of(2021, 7, 15)),
                        new Task(TaskStatus.COMPLETED,
                                LocalDate.of(2021, 7, 15),
                                "gsvm1115")), Problem.class, params);

        assertEquals(Status.NOT_FOUND, problem.getStatus());

    }
}
