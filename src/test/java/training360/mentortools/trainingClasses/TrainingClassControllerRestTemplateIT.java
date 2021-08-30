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

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(statements = "delete from training_classes")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrainingClassControllerRestTemplateIT {


    @Autowired
    TestRestTemplate template;

    TrainingClassDto trainingClass;

    TrainingClassDto trainingClass2;

    static String URL_TRAINING_CLASS = "/api/training-classes";
    static String URL_TRAINING_CLASS_ID = "/api/training-classes/{id}";

    static Map<String, String> params = new HashMap<>();

    @BeforeEach
    void init() {
        trainingClass = template.postForObject(URL_TRAINING_CLASS, new CreateTrainingClassCommand(
                        "java kezdő",
                        LocalDate.of(2021,5,10),
                        LocalDate.of(2021,7,10)),
                TrainingClassDto.class);

        trainingClass2 = template.postForObject(URL_TRAINING_CLASS, new CreateTrainingClassCommand(
                        "java haladó",
                        LocalDate.of(2021,10,12),
                        LocalDate.of(2021,12,12)),
                TrainingClassDto.class);
    }

    @Test
    void testGetTrainingClasses() {

        params.put("id", trainingClass.getId().toString());
        List<TrainingClassDto> trainingClass = template.exchange(
                URL_TRAINING_CLASS,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TrainingClassDto>>() {
                }, params)
                .getBody();

        assertThat(trainingClass)
                .extracting(TrainingClassDto::getName)
                .containsExactly("java kezdő","java haladó");
    }


    @Test
    void testFindById() {

        params.put("id", trainingClass.getId().toString());
        TrainingClassDto result = template
                .getForObject(URL_TRAINING_CLASS_ID, TrainingClassDto.class, params);

        assertEquals(LocalDate.of(2021,7,10), result.getEndDate());
    }


    @Test
    void testCreateTrainingClass() {

        assertEquals("java kezdő", trainingClass.getName());
        assertEquals(LocalDate.of(2021, 5, 10), trainingClass.getStartDate());
        assertEquals(LocalDate.of(2021, 7, 10), trainingClass.getEndDate());
    }


    @Test
    void testUpdateTrainingClass() {

        params.put("id", trainingClass2.getId().toString());
        TrainingClassDto result = template.exchange(
                URL_TRAINING_CLASS_ID,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateTrainingClassCommand("pyton haladó",
                        LocalDate.of(2021,10,12),
                        LocalDate.of(2021,12,12))),
                TrainingClassDto.class, params).getBody();

        assertEquals("pyton haladó", result.getName());
    }


    @Test
    void testDeleteTrainingClass() {
        params.put("id", trainingClass2.getId().toString());

        template.delete(URL_TRAINING_CLASS_ID, params);

        List<TrainingClassDto> result = template.exchange(URL_TRAINING_CLASS,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TrainingClassDto>>() {
                }, params).getBody();

        assertThat(result).
                hasSize(1);
    }


    @Test
    void TestInvalidName() {

        params.put("id", trainingClass2.getId().toString());
        Problem problem = template.postForObject(URL_TRAINING_CLASS, new CreateTrainingClassCommand(
                        " ", LocalDate.of(2021, 10, 12),
                        LocalDate.of(2021, 12, 12)),
                Problem.class, params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }


    @Test
    void TestInvalidStartTime() {

        params.put("id", trainingClass2.getId().toString());
        Problem problem = template.postForObject(URL_TRAINING_CLASS, new CreateTrainingClassCommand(
                        "java haladó",
                       null,
                        LocalDate.of(2021, 12, 12)),
                Problem.class, params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }
}
