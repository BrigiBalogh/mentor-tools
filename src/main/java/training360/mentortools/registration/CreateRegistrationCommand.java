package training360.mentortools.registration;

import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRegistrationCommand {

    @NotNull
    @Schema(description = "id of Student", example = "11")
    private Long studentId;

}
