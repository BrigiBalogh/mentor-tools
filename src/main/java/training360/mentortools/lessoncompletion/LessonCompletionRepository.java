package training360.mentortools.lessoncompletion;

import org.springframework.data.jpa.repository.JpaRepository;
import training360.mentortools.lessons.Lesson;
import training360.mentortools.registration.Registration;

import java.util.List;
import java.util.Optional;

public interface LessonCompletionRepository extends JpaRepository<LessonCompletion, LessonCompletionId> {


    List<LessonCompletion> findAllByStudent_Id(Long id);

    Optional<LessonCompletion> findByIdAndLesson_Id(Long id, Long lessonId);

}
