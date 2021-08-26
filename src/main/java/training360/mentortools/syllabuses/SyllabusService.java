package training360.mentortools.syllabuses;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training360.mentortools.NotFoundException;
import training360.mentortools.modules.ModuleService;

import java.lang.reflect.Type;
import java.util.List;

@AllArgsConstructor
@Service
public class SyllabusService {


    private final ModelMapper mapper;

    private final SyllabusRepository syllabusRepository;
    private  final ModuleService moduleService;


    public List<SyllabusDto> getSyllabuses() {

        Type targetListType = new TypeToken<List<SyllabusDto>>() {
        }.getType();
        return mapper.map(syllabusRepository.findAll(), targetListType);
    }


    public SyllabusDto findSyllabusById(long id) {

        Syllabus syllabus = findSyllabus(id);
        return mapper.map(syllabus, SyllabusDto.class);
    }


    @Transactional
    public SyllabusDto createSyllabus(CreateSyllabusCommand command) {
        Syllabus syllabus = new Syllabus(command.getName());
        syllabusRepository.save(syllabus);
        return mapper.map(syllabus, SyllabusDto.class);
    }


    @Transactional
    public SyllabusDto updateSyllabusById(long id, UpdateSyllabusCommand command) {

        Syllabus syllabus = findSyllabus(id);
        syllabus.setName(command.getName());
        return mapper.map(syllabus, SyllabusDto.class);
    }

    public void deleteSyllabus(Long id) {
        syllabusRepository.deleteById(id);
    }


    public Syllabus findSyllabus(long id) {
        return syllabusRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Transactional
    public SyllabusWithModuleDto addModuleToSyllabus(Long id, AddModuleToSyllabusCommand command) {
        Syllabus syllabus = findSyllabus(id);
        Module module = moduleService.findModule(command.getModuleId());
        syllabus.addModule(module);
        return mapper.map(syllabus, SyllabusWithModuleDto.class);
    }

    public  SyllabusWithModuleDto findModulesWithSyllabus(Long id) {
        Syllabus syllabus = findSyllabus(id);
        return mapper.map(syllabus, SyllabusWithModuleDto.class);
    }
}
