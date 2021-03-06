package training360.mentortools.registration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationId implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "training_class_id")
    private Long trainingClassId;
}
