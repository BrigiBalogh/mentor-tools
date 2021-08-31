package training360.mentortools.lessons;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateLessonCommand {

    @Schema(description = "title of a lesson ", example = "JPA bevezetés")
    @NotBlank(message = "title can not be empty")
    @Length(max = 255, message = "lesson's title max length is 255")
    private String title;


    @Schema(description = "URL of the lesson ", example = "/modules")
    @NotBlank(message = "URL can not be empty")
    @Length(max = 255, message = "lesson's URL max length is 255")
    private String url;
}
