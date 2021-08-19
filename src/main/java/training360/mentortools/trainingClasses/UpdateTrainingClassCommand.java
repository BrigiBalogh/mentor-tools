package training360.mentortools.trainingClasses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@IsValidDate
public class UpdateTrainingClassCommand {

    @Schema(description = "name of the training class", example = "java kezdő")
    @NotBlank(message = "Name can not be empty")
    @Length(max = 255)
    private String name;

    @Schema(description = "start date of the training class", example = "2021-03-15")
    private LocalDate startDate;

    @Schema(description = "end date of the training class", example = "2021-06-18")
    private LocalDate endDate;

}
