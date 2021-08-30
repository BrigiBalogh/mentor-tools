package training360.mentortools.lessoncompletion;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training360.mentortools.lessons.*;
import training360.mentortools.modules.Module;
import training360.mentortools.students.Student;
import training360.mentortools.students.StudentService;

import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class LessonCompletionService {


    private final ModelMapper mapper;
    private final LessonCompletionRepository lessonCompletionRepository;
    private final LessonService lessonService;
    private final StudentService studentService;


    public List<LessonCompletionDto> getLessonsWithCompletions(Long id) {
        Type targetListType = new TypeToken<List<LessonCompletionDto>>() {
        }.getType();
        return mapper.map(lessonCompletionRepository.findAllByStudent_Id(id), targetListType);
    }

    public LessonCompletionDto findLessonWithCompletionById(long id, Long lessonId) {
        LessonCompletion lessonCompletion = findLessonCompletionByIdAndLessonId(id, lessonId);
        return mapper.map(lessonCompletion, LessonCompletionDto.class);
    }

    @Transactional
    public LessonCompletionDto createLessonCompletion(Long id, CreateLessonCompletionCommand command) {

        Student student = studentService.findStudent(id);
        Lesson lesson = lessonService.findById(command.getLessonId());
        LessonCompletion lessonCompletion =
                new LessonCompletion(new LessonCompletionId(id, command.getLessonId()),
                        student, lesson, command.getTask(), command.getVideo());
        lessonCompletionRepository.save(lessonCompletion);
        return mapper.map(lessonCompletion, LessonCompletionDto.class);
    }


    @Transactional
    public LessonCompletionDto UpdateLessonCompletionById(long id, UpdateLessonCompletionCommand command) {
        LessonCompletion lessonCompletion = findLessonCompletionByIdAndLessonId(id, command.getLessonId());

        lessonCompletion.setVideo(command.getVideo());
        lessonCompletion.setTask(command.getTask());
        return mapper.map(lessonCompletion, LessonCompletionDto.class);
    }

 /*   @Transactional
    public LessonCompletionDto updateLessonCompletion(Long id, UpdateLessonCompletionCommand command) {
        LessonCompletion lessonCompletion = findById(new LessonCompletionId(id, command.getLessonId()));
        lessonCompletion.setVideo(command.getVideo());
        lessonCompletion.setTask(command.getTask());
        return mapper.map(lessonCompletion, LessonCompletionDto.class);
    }  */

    public void deleteLessonCompletion(long id, Long lessonId) {
        lessonCompletionRepository.delete(findLessonCompletionByIdAndLessonId(id, lessonId));
        //  lessonCompletionRepository.deleteById(new LessonCompletionId(id,lessonId));
    }

    private LessonCompletion findLessonCompletionByIdAndLessonId(long id, long lessonId) {
        return lessonCompletionRepository.findByIdAndLesson_Id(id, lessonId)
                .orElseThrow(() -> new NotFoundStudentIdAndLessonIdException(id, lessonId));
    }
/*
    public LessonCompletion findById(LessonCompletionId id) {
        return lessonCompletionRepository.findById(id).orElseThrow(() -> new LessonCompletionNotFoundException(id));
    }    */
}



