package training360.mentortools.syllabuses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSyllabusCommand {


    @Schema(description = "name of the syllabus ", example = "java")
    @NotBlank(message = "Name can not be empty")
    @Length(max = 255, message = "syllabus's name max length is 255")
    private String name;
}
