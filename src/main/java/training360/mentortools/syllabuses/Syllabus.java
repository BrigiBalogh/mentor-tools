package training360.mentortools.syllabuses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training360.mentortools.trainingClasses.TrainingClass;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "syllabuses")
public class Syllabus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "syllabuses")
    public Set<TrainingClass> trainingClasses;

    @ManyToMany
    @JoinTable(name = "syllabuses_modules", joinColumns = @JoinColumn(name = "syllabus_id"),
            inverseJoinColumns = @JoinColumn(name = "module_id"))
    public Set<Module> modules;


    public Syllabus(String name) {
        this.name = name;
    }

    public void addTrainingClass(TrainingClass trainingClass) {
        if(trainingClasses == null) {
            trainingClasses = new HashSet<>();
        }
        trainingClasses.add(trainingClass);
        trainingClass.setSyllabus(this);
    }

    public void addModule(Module module) {
        if(modules == null) {
            modules = new HashSet<>();
        }
        modules.add(module);

    }
}
