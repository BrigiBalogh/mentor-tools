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
import training360.mentortools.modules.CreateModuleCommand;
import training360.mentortools.modules.ModuleDto;
import training360.mentortools.modules.UpdateModuleCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(statements = "delete from modules")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ModuleControllerRestTemplateIT {


    @Autowired
    TestRestTemplate template;

    ModuleDto module;
    ModuleDto module1;
    static String URL_MODULE = "/api/modules";
    static String URL_MODULE_ID = "/api/modules/{id}";

    static Map<String, String> params = new HashMap<>();


    @BeforeEach
    void init() {
        module = template.postForObject(URL_MODULE, new CreateModuleCommand("JPA", "/modules"),
                ModuleDto.class);

        module1 = template.postForObject(URL_MODULE, new CreateModuleCommand("Git", "/git"),
                ModuleDto.class);
    }

    @Test
    void testGetModules() {

        params.put("id", module.getId().toString());
        List<ModuleDto> modules = template.exchange(
                URL_MODULE,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ModuleDto>>() {
                }, params)
                .getBody();

        assertThat(modules)
                .extracting(ModuleDto::getTitle)
                .containsExactly("JPA", "Git");
    }


    @Test
    void testFindById() {

        params.put("id", module.getId().toString());
        ModuleDto result = template
                .getForObject(URL_MODULE_ID, ModuleDto.class, params);

        assertEquals("JPA", result.getTitle());
    }


    @Test
    void testCreateModule() {

        assertEquals("JPA", module.getTitle());
        assertEquals("/modules", module.getUrl());
    }


    @Test
    void testUpdateModule() {

        params.put("id", module1.getId().toString());
        ModuleDto result = template.exchange(
                URL_MODULE_ID,
                HttpMethod.PUT,
                new HttpEntity<>(new UpdateModuleCommand(
                        "Git kezdő", "/git-kezdo")),
                ModuleDto.class, params).getBody();

        assertEquals("Git kezdő", result.getTitle());

    }


    @Test
    void testDeleteModule() {
        params.put("id", module1.getId().toString());

        template.delete(URL_MODULE_ID, params);

        List<ModuleDto> result = template.exchange(URL_MODULE,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ModuleDto>>() {
                }, params).getBody();

        assertThat(result).
                hasSize(1);
    }


    @Test
    void TestInvalidTitle() {

        params.put("id", module.getId().toString());
        Problem problem = template.postForObject(URL_MODULE, new CreateModuleCommand(
                " ", "/modules"), Problem.class, params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }


    @Test
    void TestInvalidTitleLength() {

        params.put("id", module.getId().toString());
        Problem problem = template.postForObject(URL_MODULE, new CreateModuleCommand(
                        "JPA".repeat(256), "/modules"),
                Problem.class, params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }

    @Test
    void testModuleNotFound() {
        params.put("id", "-1");
        Problem problem = template.getForObject(URL_MODULE_ID, Problem.class, params);

        assertEquals(Status.NOT_FOUND, problem.getStatus());
        assertEquals("Not found with id '-1'", problem.getDetail());
    }


    @Test
    void TestInvalidUrl() {

        params.put("id", module.getId().toString());
        Problem problem = template.postForObject(URL_MODULE, new CreateModuleCommand(
                "JPA ", " "), Problem.class, params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }


    @Test
    void TestInvalidUrlLength() {

        params.put("id", module.getId().toString());
        Problem problem = template.postForObject(URL_MODULE, new CreateModuleCommand(
                        "JPA", "/modules".repeat(256)),
                Problem.class, params);

        assertEquals(Status.BAD_REQUEST, problem.getStatus());
        assertEquals("Constraint Violation", problem.getTitle());
    }
}
