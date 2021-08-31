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
import training360.mentortools.syllabuses.CreateSyllabusCommand;
import training360.mentortools.syllabuses.SyllabusDto;
import training360.mentortools.syllabuses.UpdateSyllabusCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(statements = "delete from syllabuses")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SyllabusControllerRestTemplateIT {


    @Autowired
    TestRestTemplate template;

    SyllabusDto syllabus;
    SyllabusDto syllabus1;
    static String URL_SYLLABUS = "/api/syllabuses";
    static String URL_SYLLABUS_ID = "/api/syllabuses/{id}";

    static Map<String, String> params = new HashMap<>();


    @BeforeEach
    void init() {
        syllabus = template.postForObject(URL_SYLLABUS, new CreateSyllabusCommand("java"),
                SyllabusDto.class);

        syllabus1 = template.postForObject(URL_SYLLABUS, new CreateSyllabusCommand("tesztelő"),
                SyllabusDto.class);
    }

    @Test
    void testGetSyllabuses() {

        params.put("id", syllabus.getId().toString());
        List<SyllabusDto> syllabuses = template.exchange(
                URL_SYLLABUS,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SyllabusDto>>() {
                }, params)
                .getBody();

        assertThat(syllabuses)
                .extracting(SyllabusDto::getName)
                .containsExactly("java", "tesztelő");
    }


    @Test
    void testFindById() {

        params.put("id", syllabus.getId().toString());
        SyllabusDto result = template
                .getForObject(URL_SYLLABUS_ID, SyllabusDto.class, params);

        assertEquals("java", result.getName());
    }


    @Test
    void testCreateSyllabus() {

        assertEquals("java", syllabus.getName());
    }


    @Test
    void testUpdateSyllabus() {

        params.put("id", syllabus1.getId().toString());
        SyllabusDto result = template.exchange(
                URL_SYLLABUS_ID,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateSyllabusCommand(
                        "tesztelő kezdő")),
                SyllabusDto.class, params).getBody();

        assertEquals("tesztelő kezdő", result.getName());

    }


    @Test
    void testDeleteSyllabus() {
        params.put("id", syllabus1.getId().toString());

        template.delete(URL_SYLLABUS_ID, params);

        List<SyllabusDto> result = template.exchange(URL_SYLLABUS,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SyllabusDto>>() {
                }, params).getBody();

        assertThat(result).
                hasSize(1);
    }


    @Test
    void TestInvalidName() {

        params.put("id", syllabus.getId().toString());
        Problem problem = template.postForObject(URL_SYLLABUS, new CreateSyllabusCommand(
                " "), Problem.class, params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }


    @Test
    void TestInvalidNameLength() {

        params.put("id", syllabus.getId().toString());
        Problem problem = template.postForObject(URL_SYLLABUS, new CreateSyllabusCommand(
                        "b".repeat(256)),
                Problem.class, params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

    @Test
    void testSyllabusNotFound() {
        params.put("id", "-1");
        Problem problem = template.getForObject(URL_SYLLABUS_ID, Problem.class, params);

        assertEquals(Status.NOT_FOUND, problem.getStatus());
        assertEquals("Not found with id '-1'", problem.getDetail());
    }
}
