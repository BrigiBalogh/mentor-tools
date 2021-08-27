package training360.mentortools.lessons;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training360.mentortools.modules.ModuleService;
import training360.mentortools.modules.Module;

import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class LessonService {

    private final ModelMapper mapper;
    private  final LessonRepository lessonRepository;
    private  final ModuleService moduleService;


    public List<LessonDto> getLessons(Long id) {
        Type targetListType = new TypeToken<List<LessonDto>>() {
        }.getType();
        return mapper.map(lessonRepository.findAllByModule_Id(id), targetListType);
    }


    public LessonDto findLessonById(long id, Long lessonId) {
        Lesson lesson =findModuleByIdAndLessonId(id,lessonId);
        return mapper.map(lesson,LessonDto.class);
    }

    @Transactional
    public LessonDto createLesson(Long id, CreateLessonCommand command) {
        Module module = moduleService.findModule(id);
        Lesson lesson = new Lesson(command.getTitle(), command.getUrl(), module);
        lessonRepository.save(lesson);
        return mapper.map(lesson, LessonDto.class);
    }




    @Transactional
    public LessonDto UpdateLessonById(long id, Long lessonId, UpdateLessonCommand command) {
        Lesson lesson = findModuleByIdAndLessonId(id,lessonId);
        lesson.setTitle(command.getTitle());
        lesson.setUrl(command.getUrl());

        return mapper.map(lesson, LessonDto.class);
    }


    public void deleteLesson(long id, Long lessonId) {
        lessonRepository.delete(findModuleByIdAndLessonId(lessonId, id));
    }


    private Lesson findModuleByIdAndLessonId(long id, long lessonId) {
        return lessonRepository.findByIdAndModule_Id( lessonId, id)
                .orElseThrow(() -> new NotFoundModuleIdAndLessonIdException(id, lessonId) );
    }
}
