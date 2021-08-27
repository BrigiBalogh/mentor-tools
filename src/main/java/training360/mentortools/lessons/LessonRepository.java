package training360.mentortools.lessons;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {



    List<Lesson> findAllByModule_Id(Long id);

    Optional<Lesson> findByIdAndModule_Id(Long id, Long moduleId);

}
