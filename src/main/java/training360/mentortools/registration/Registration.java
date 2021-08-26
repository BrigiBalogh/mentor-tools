package training360.mentortools.registration;

import lombok.*;
import training360.mentortools.students.Student;
import training360.mentortools.trainingClasses.TrainingClass;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registrations")
public class Registration {


    @EmbeddedId
    @Column(name ="registration_id" )
    private RegistrationId registrationId;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @MapsId("trainingClassId")
    @JoinColumn(name = "training_class_id")
    private TrainingClass trainingClass;


    public Registration(RegistrationId registrationId,Student student, TrainingClass trainingClass) {
        this.registrationId = registrationId;
        this.student = student;
        this.trainingClass = trainingClass;
        this.status = RegistrationStatus.ACTIVE;
    }

    public Registration(RegistrationId registrationId) {
        this.registrationId = registrationId;
    }
}
