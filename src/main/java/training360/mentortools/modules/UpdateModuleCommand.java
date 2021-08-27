package training360.mentortools.modules;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateModuleCommand {

    @Schema(description = "title of a module ", example = " JPA")
    @NotBlank(message = "title can not be empty")
    @Length(max = 255, message = "module's title max length is 255")
    private String title;

    @Schema(description = "URL of the module ", example = "/modules")
    @NotBlank(message = "URL can not be empty")
    @Length(max = 255, message = "module's URL max length is 255")
    private String url;
}
