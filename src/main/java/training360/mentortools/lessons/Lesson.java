package training360.mentortools.lessons;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
