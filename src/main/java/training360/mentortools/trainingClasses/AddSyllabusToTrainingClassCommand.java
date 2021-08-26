package training360.mentortools.trainingClasses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSyllabusToTrainingClassCommand {


    @Schema(description = "id of the syllabus", example = "11")
    private Long syllabusId;
}
