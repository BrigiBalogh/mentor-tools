package training360.mentortools.trainingClasses;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@Sql(statements = "delete from training_classes")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTemplateIT {
}
