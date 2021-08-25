package training360.mentortools.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training360.mentortools.students.StudentDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationOfStudentDto {

    private Long id;
    private String name;
    private RegistrationStatus status;

}
