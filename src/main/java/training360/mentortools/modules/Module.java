package training360.mentortools.modules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training360.mentortools.lessons.Lesson;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "modules")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private String title;

    private String url;

    @OneToMany(mappedBy = "module",cascade = CascadeType.ALL)
    private Set<Lesson> lessons;


    public Module(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
