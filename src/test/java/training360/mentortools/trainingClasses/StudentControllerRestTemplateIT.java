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
import training360.mentortools.students.CreateStudentCommand;
import training360.mentortools.students.StudentDto;
import training360.mentortools.students.UpdateStudentCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(statements = "delete from students")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    StudentDto student;

    StudentDto student1;

    static String URL_STUDENT = "/api/students";
    static String URL_STUDENT_ID = "/api/students/{id}";

    static Map<String, String> params = new HashMap<>();


    @BeforeEach
    void init() {
        student = template.postForObject(URL_STUDENT, new CreateStudentCommand(
                        "Kiss István", "kiss.istvan@gmail.com"
                        , "Kiss.Istvan", "lemaradt"),
                StudentDto.class);

        student1 = template.postForObject(URL_STUDENT, new CreateStudentCommand(
                        "Kiss Zsuzsanna", "kiss.zsuzsa@gmail.com"
                        , "Kiss.Zsuzsa", "halad"),
                StudentDto.class);
    }

    @Test
    void testGetStudents() {

        params.put("id", student.getId().toString());
        List<StudentDto> students = template.exchange(
                URL_STUDENT,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StudentDto>>() {
                }, params)
                .getBody();

        assertThat(students)
                .extracting(StudentDto::getName)
                .containsExactly("Kiss István", "Kiss Zsuzsanna");
    }


    @Test
    void testFindById() {

        params.put("id", student.getId().toString());
        StudentDto result = template
                .getForObject(URL_STUDENT_ID, StudentDto.class, params);

        assertEquals("kiss.istvan@gmail.com", result.getEmail());
    }


    @Test
    void testCreateStudent() {

        assertEquals("Kiss Zsuzsanna", student1.getName());
        assertEquals("Kiss.Zsuzsa", student1.getGithubUsername());
        assertEquals("halad", student1.getComment());
    }


    @Test
    void testUpdateStudent() {

        params.put("id", student1.getId().toString());
        StudentDto result = template.exchange(
                URL_STUDENT_ID,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateStudentCommand(
                        "Kiss Zsuzsanna",
                        "kiss.box@gmail.com"
                        , "Kiss.Zsuzsa",
                        "lemaradt")),
                StudentDto.class, params).getBody();

        assertEquals("kiss.box@gmail.com", result.getEmail());
        assertEquals("lemaradt", result.getComment());
    }


    @Test
    void testDeleteStudent() {
        params.put("id", student.getId().toString());

        template.delete(URL_STUDENT_ID, params);

        List<StudentDto> result = template.exchange(URL_STUDENT,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StudentDto>>() {
                }, params).getBody();

        assertThat(result).
                hasSize(1);
    }


    @Test
    void TestInvalidName() {

        params.put("id", student1.getId().toString());
        Problem problem = template.postForObject(URL_STUDENT, new CreateStudentCommand(
                        " ", "kiss.zsuzsa@gmail.com"
                        , "Kiss.Zsuzsa", "halad"),
                Problem.class, params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }


    @Test
    void TestInvalidEmail() {

        params.put("id", student1.getId().toString());
        Problem problem = template.postForObject(URL_STUDENT, new CreateStudentCommand(
                        "Kiss Zsuzsanna", " "
                        , "Kiss.Zsuzsa", "halad"),
                Problem.class, params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }
}


