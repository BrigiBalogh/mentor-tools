package training360.mentortools.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training360.mentortools.students.StudentDto;
import training360.mentortools.trainingClasses.TrainingClassDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {

    private RegistrationStatus status;

    private StudentDto student;

    private TrainingClassDto trainingClass;
}
