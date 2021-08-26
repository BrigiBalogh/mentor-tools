package training360.mentortools.syllabuses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddModuleToSyllabusCommand {

    @Schema(description = "id of the module")
    private Long moduleId;
}
