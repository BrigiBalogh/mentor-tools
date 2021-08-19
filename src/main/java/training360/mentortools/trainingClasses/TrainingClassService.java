package training360.mentortools.trainingClasses;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training360.mentortools.NotFoundException;

import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class TrainingClassService {


    private ModelMapper mapper;

    private TrainingClassRepository trainingClassRepository;

    public List<TrainingClassDto> getTrainingClasses() {

        Type targetListType = new TypeToken<List<TrainingClassDto>>() {
        }.getType();
        return mapper.map(trainingClassRepository.findAll(), targetListType);
    }


    public TrainingClassDto findTrainingClassById(long id) {

        TrainingClass trainingClass = findTrainingClass(id);

        return mapper.map(trainingClass, TrainingClassDto.class);
    }


    public TrainingClassDto createTrainingClass(CreateTrainingClassCommand command) {

        TrainingClass trainingClass = new TrainingClass(command.getName(), command.getStartDate(),
                command.getEndDate());
        trainingClassRepository.save(trainingClass);
        return mapper.map(trainingClass, TrainingClassDto.class);
    }
    @Transactional
    public TrainingClassDto updateTrainingClassById(long id, UpdateTrainingClassCommand command) {

        TrainingClass trainingClass = findTrainingClass(id);

        trainingClass.setName(command.getName());
        trainingClass.setStartDate(command.getStartDate());
        trainingClass.setEndDate(command.getEndDate());

        return mapper.map(trainingClass, TrainingClassDto.class);
    }


    public void deleteTrainingClass(long id) {
            trainingClassRepository.deleteById(id);
    }

    private TrainingClass findTrainingClass(long id) {
        return trainingClassRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }
}
