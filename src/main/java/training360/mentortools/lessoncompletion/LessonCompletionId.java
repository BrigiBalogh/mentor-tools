package training360.mentortools.lessoncompletion;

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
public class LessonCompletionId implements Serializable {

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "lesson_id")
    private Long lessonId;

}
