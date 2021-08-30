package training360.mentortools.lessoncompletion;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLessonCompletionCommand {

    @Schema(description = "id of a lesson ", example = "12345")
    private Long lessonId;

    @Schema(description = "video of a lesson completion ", example = "bevezetés a JPA-ba")
    private Video video;

    @Schema(description = "task of a lesson completion ", example = "bevezetés a JPA-ba 1. feladat")
    private Task task;
}
