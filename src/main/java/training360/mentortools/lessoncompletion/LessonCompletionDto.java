package training360.mentortools.lessoncompletion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training360.mentortools.lessons.LessonDto;
import training360.mentortools.students.StudentDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonCompletionDto {


    private StudentDto student;
    private LessonDto lesson;
    private Task task;
    private Video video;
}
