package training360.mentortools.students;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStudentCommand {


    @Schema(description = "name of the student ", example = "Kiss István")
    @NotBlank(message = "Name can not be empty")
    @Length(max = 255, message = "Student's name max length is 255")
    private String name;

    @Schema(description = "email of the student ", example = "kiss.istvan@gmail.com")
    @NotBlank(message = "email can not be empty")
    @Length(max = 255, message = "Student's email max length is 255")
    private String email;

    @Schema(description = "github username of the student ", example = "Kiss.Istvan")
    @Length(max = 255, message = "Student's github username max length is 255")
    private String githubUsername;

    @Schema(description = "comment of the student ", example = "lemaradt")
    private String comment;
}
