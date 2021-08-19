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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationId;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "training_class_id")
    private TrainingClass trainingClass;







}
