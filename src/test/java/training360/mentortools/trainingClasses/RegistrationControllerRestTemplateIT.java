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
import training360.mentortools.registration.*;
import training360.mentortools.students.CreateStudentCommand;
import training360.mentortools.students.StudentDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(statements = {"alter table registrations drop foreign key if exists fk_training_class ",
        "alter table registrations drop foreign key if exists fk_student",
        "delete from students","delete from training_classes","delete from registrations"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegistrationControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    TrainingClassDto trainingClass;

    TrainingClassDto trainingClass1;

    // RegistrationOfStudentDto registration;

    StudentDto student;

    StudentDto student1;

    static String URL_STUDENT = "/api/students";
    static String URL_TRAINING_CLASS_REGISTRATION = "/api/training-classes/{id}/registrations";
    static String URL_STUDENT_REGISTRATION = "/api/students/{id}/registrations";
    static String URL_TRAINING_CLASS = "/api/training-classes";

    static Map<String, String> params = new HashMap<>();


    @BeforeEach
    void init() {
        trainingClass = template.postForObject(URL_TRAINING_CLASS, new CreateTrainingClassCommand(
                        "java kezdő",
                        LocalDate.of(2021, 5, 10),
                        LocalDate.of(2021, 7, 10)),
                TrainingClassDto.class);

        trainingClass1 = template.postForObject(URL_TRAINING_CLASS, new CreateTrainingClassCommand(
                        "java haladó",
                        LocalDate.of(2021, 10, 12),
                        LocalDate.of(2021, 12, 12)),
                TrainingClassDto.class);


        student = template.postForObject(URL_STUDENT, new CreateStudentCommand(
                        "Kiss István", "kiss.istvan@gmail.com"
                        , "Kiss.Istvan", "lemaradt"),
                StudentDto.class);

        student1 = template.postForObject(URL_STUDENT, new CreateStudentCommand(
                        "Kiss Zsuzsanna", "kiss.zsuzsa@gmail.com"
                        , "Kiss.Zsuzsa", "halad"),
                StudentDto.class);

     /*  registration = template.postForObject(URL_TRAINING_CLASS_REGISTRATION,
                new CreateRegistrationCommand(student.getId()),
                RegistrationOfStudentDto.class,
                params);*/
    }

    @Test
    void createRegistrationTest() {
        params.put("id", trainingClass.getId().toString());
        RegistrationOfStudentDto registration = template.postForObject(URL_TRAINING_CLASS_REGISTRATION,
                new CreateRegistrationCommand(student.getId()),
                RegistrationOfStudentDto.class,
                params);

        assertEquals(RegistrationStatus.ACTIVE, registration.getStatus());
        assertEquals("Kiss István", registration.getName());
        assertEquals(student.getId(), registration.getId());
    }

    @Test
    void getRegistrationsByTrainingClassIdTest() {

        params.put("id", trainingClass.getId().toString());
        template.postForObject(URL_TRAINING_CLASS_REGISTRATION,
                new CreateRegistrationCommand(student.getId()),
                RegistrationOfStudentDto.class,
                params);
        template.postForObject(URL_TRAINING_CLASS_REGISTRATION,
                new CreateRegistrationCommand(student1.getId()),
                RegistrationOfStudentDto.class,
                params);


        List<RegistrationOfStudentDto> result = template.exchange(URL_TRAINING_CLASS_REGISTRATION,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RegistrationOfStudentDto>>() {
                },
                params).getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(RegistrationOfStudentDto::getName)
                .containsExactly("Kiss István", "Kiss Zsuzsanna");
    }

    @Test
    void registrationByStudentTest() {
        params.put("id", trainingClass.getId().toString());
        template.postForObject(URL_TRAINING_CLASS_REGISTRATION,
                new CreateRegistrationCommand(student.getId()),
                RegistrationOfStudentDto.class,
                params);

        params.put("id", trainingClass1.getId().toString());
        template.postForObject(URL_TRAINING_CLASS_REGISTRATION,
                new CreateRegistrationCommand(student1.getId()),
                RegistrationOfStudentDto.class,
                params);
        params.put("id", student.getId().toString());

        List<RegistrationOfTrainingClassDto> result = template.exchange(URL_STUDENT_REGISTRATION,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<RegistrationOfTrainingClassDto>>() {
                }, params).getBody();

        assertThat(result)
                .hasSize(2)
                .extracting(RegistrationOfTrainingClassDto::getTrainingClassName)
                .containsExactly("java kezdő", "java haladó");
    }


    @Test
    void updateRegistrationStatus() {
        params.put("id", trainingClass.getId().toString());
        template.postForObject(URL_TRAINING_CLASS_REGISTRATION,
                new CreateRegistrationCommand(student.getId()),
                RegistrationOfStudentDto.class, params);

        RegistrationOfStudentDto result = template.exchange(
                URL_TRAINING_CLASS_REGISTRATION,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateRegistrationCommand(student.getId(),
                        RegistrationStatus.EXIT_IN_PROGRESS)),
                RegistrationOfStudentDto.class, params).getBody();

        assertEquals(RegistrationStatus.EXIT_IN_PROGRESS, result.getStatus());

    }
}
