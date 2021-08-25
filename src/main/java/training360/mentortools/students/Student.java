package training360.mentortools.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training360.mentortools.registration.Registration;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    @Column(name = "github_username")
    private String githubUsername;

    private String comment;


    @OneToMany(mappedBy = "trainingClass")
    private Set<Registration> registrations;

    public Student(String name, String email, String githubUsername, String comment) {
        this.name = name;
        this.email = email;
        this.githubUsername = githubUsername;
        this.comment = comment;
    }
}
