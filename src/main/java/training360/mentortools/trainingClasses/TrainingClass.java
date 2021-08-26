package training360.mentortools.trainingClasses;

import lombok.*;
import training360.mentortools.registration.Registration;
import training360.mentortools.syllabuses.Syllabus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "training_classes")
public class TrainingClass {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(mappedBy = "trainingClass")
    Set<Registration> registrations;

    @ManyToOne
    @JoinColumn(name = "syllabus_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Syllabus syllabus;


    public TrainingClass(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
