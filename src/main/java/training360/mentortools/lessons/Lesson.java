package training360.mentortools.lessons;


import lombok.*;
import training360.mentortools.modules.Module;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lessons")
public class Lesson {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String url;

    @ManyToOne
    @JoinColumn(name = "module_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Module module;


    public Lesson(String title, String url, Module module) {
        this.title = title;
        this.url = url;
        this.module = module;
    }
}
