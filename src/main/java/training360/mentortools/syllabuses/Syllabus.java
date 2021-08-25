package training360.mentortools.syllabuses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training360.mentortools.registration.Registration;
import training360.mentortools.trainingClasses.TrainingClass;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "guests")
public class Syllabus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "syllabus")
    private Set<TrainingClass> trainingClasses;


    public Syllabus(String name) {
        this.name = name;
    }
}
