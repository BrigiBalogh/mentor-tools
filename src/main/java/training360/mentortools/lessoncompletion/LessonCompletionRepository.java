package training360.mentortools.lessoncompletion;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LessonCompletionRepository extends JpaRepository<LessonCompletion, LessonCompletionId> {


    List<LessonCompletion> findAllByStudent_Id(Long id);

    Optional<LessonCompletion> findByIdAndLesson_Id(Long id, Long lessonId);

}
