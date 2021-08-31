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
import training360.mentortools.lessons.CreateLessonCommand;
import training360.mentortools.lessons.LessonDto;
import training360.mentortools.lessons.UpdateLessonCommand;
import training360.mentortools.modules.CreateModuleCommand;
import training360.mentortools.modules.ModuleDto;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(statements = {"delete from modules", "delete from lessons"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LessonControllerRestTemplateIT {



    @Autowired
    TestRestTemplate template;

    ModuleDto module;

    ModuleDto module1;

    static String URL_MODULE = "/api/modules";
    static String URL_LESSON = "/api/modules/{id}/lessons";
    static String URL_LESSON_ID = "/api/modules/{id}/lessons/{lessons-id}";

    static Map<String, String> params = new HashMap<>();

    @BeforeEach
    void init() {
        module = template.postForObject(URL_MODULE,
                new CreateModuleCommand("JPA", "/modules"),
                ModuleDto.class);

        module1 = template.postForObject(URL_MODULE,
                new CreateModuleCommand("Git", "/git"),
                ModuleDto.class);
    }


    @Test
    void testGetLessons() {


        params.put("id", module.getId().toString());
        LessonDto lesson =
                template.postForObject(
                        URL_LESSON,
                        new CreateLessonCommand(
                                "JPA bevezetes",
                                "/modules/jpa-bevezetes"),
                        LessonDto.class, params);

        assertEquals("JPA bevezetes", lesson.getTitle());


        params.put("id", module1.getId().toString());
        template.postForObject(URL_LESSON,
                new CreateLessonCommand(
                        "git bevezetes",
                        "/modules/git-bevezetes"), LessonDto.class, params);


        List<LessonDto> lessons = template.exchange(URL_LESSON,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LessonDto>>() {
                }, params)
                .getBody();

        assertThat(lessons)
                .extracting(LessonDto::getTitle)
                .containsExactly("git bevezetes");
    }


    @Test
    void testCreateLesson() {


        params.put("id", module1.getId().toString());
        LessonDto result =
                template.postForObject(URL_LESSON,
                        new CreateLessonCommand("git bevezetes",
                                "/modules/git-bevezetes"), LessonDto.class, params);


        assertEquals("git bevezetes", result.getTitle());
        assertEquals("/modules/git-bevezetes", result.getUrl());

    }


    @Test
    void testUpdateLessonById() {


        params.put("id", module1.getId().toString());
        LessonDto lesson =
                template.postForObject(URL_LESSON,
                        new CreateLessonCommand("git bevezetes",
                                "/modules/git-bevezetes"), LessonDto.class, params);


        params.put("lessons-id", lesson.getId().toString());
        LessonDto result = template.exchange(
                URL_LESSON_ID,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateLessonCommand("gitHub", "/modules/github")),
                LessonDto.class, params).getBody();

        assertEquals("gitHub", result.getTitle());
    }


    @Test
    void testDeleteLesson() {

        params.put("id", module.getId().toString());
        LessonDto lessonDto =
                template.postForObject(
                        URL_LESSON,
                        new CreateLessonCommand(
                                "JPA bevezetes",
                                "/modules/jpa-bevezetes"),
                        LessonDto.class, params);

        params.put("lessons-id", lessonDto.getId().toString());

        template.delete(URL_LESSON_ID, params);


        List<LessonDto> result = template.exchange(URL_LESSON,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LessonDto>>() {
                }, params)
                .getBody();

        assertThat(result).
                hasSize(1);
    }


    @Test
    void testCreateLessonInvalidTitle() {


        params.put("id", module1.getId().toString());
        LessonDto result =
                template.postForObject(URL_LESSON,
                        new CreateLessonCommand("git bevezetes",
                                "/modules/git-bevezetes"), LessonDto.class, params);

        Problem problem =
                template.postForObject(URL_LESSON,
                        new CreateLessonCommand(" ",
                                "/modules/git-bevezetes"), Problem.class, params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
    }


    @Test
    void TestInvalidTitleLength() {

        params.put("id", module.getId().toString());
        Problem problem = template.postForObject(
                URL_LESSON,
                new CreateLessonCommand(
                        "JPA bevezetes".repeat(256),
                        "/modules/jpa-bevezetes"),
                Problem.class, params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }
}
