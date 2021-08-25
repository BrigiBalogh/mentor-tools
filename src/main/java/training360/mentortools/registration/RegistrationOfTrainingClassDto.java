package training360.mentortools.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationOfTrainingClassDto {


    private Long TrainingClassId;

    private String TrainingClassName;
}
