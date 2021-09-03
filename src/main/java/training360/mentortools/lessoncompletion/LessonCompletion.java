package training360.mentortools.lessoncompletion;

import lombok.*;
import training360.mentortools.lessons.Lesson;
import training360.mentortools.students.Student;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lessons_completions")
public class LessonCompletion {


    @EmbeddedId
    private LessonCompletionId id;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @MapsId("lessonId")
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @Embedded
    private Task task;

    @Embedded
    private Video video;

}
