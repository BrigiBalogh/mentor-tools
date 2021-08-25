package training360.mentortools.registration;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training360.mentortools.students.Student;
import training360.mentortools.students.StudentService;
import training360.mentortools.trainingClasses.TrainingClass;
import training360.mentortools.trainingClasses.TrainingClassService;

import java.util.List;

@Service
@AllArgsConstructor
public class RegistrationService {


    private final RegistrationRepository registrationRepository;

    private final StudentService studentService;

    private final TrainingClassService trainingClassService;

    @Transactional
    public RegistrationOfStudentDto createRegistration(Long id, CreateRegistrationCommand command) {

        TrainingClass trainingClass = trainingClassService.findTrainingClass(id);

        Student student = studentService.findStudent(command.getStudentId());

        RegistrationId registrationId = new RegistrationId(student.getId(), trainingClass.getId());
        Registration registration = new Registration(registrationId, student, trainingClass);
        registrationRepository.save(registration);
        return registrationRepository.getRegistrationOfStudentDto(registrationId);
    }


    public List<RegistrationOfStudentDto> getRegistrationByTrainingClass(Long id) {
        return registrationRepository.getRegistrationsByTrainingClassId(id);
    }

    public List<RegistrationOfTrainingClassDto> registrationByStudent(Long id) {
        return registrationRepository.getRegistrationsByStudentId(id);
    }


    public Registration findRegistration(RegistrationId registrationId) {
        return registrationRepository.findRegistrationByRegistrationId(registrationId)
                .orElseThrow(() -> new RegistrationNotFoundException(registrationId.getTrainingClassId(), registrationId.getStudentId()));
    }

    @Transactional
    public RegistrationOfStudentDto updateRegistration(long id, UpdateRegistrationCommand command) {

        TrainingClass trainingClass = trainingClassService.findTrainingClass(id);

        Student student = studentService.findStudent(command.getStudentId());

        RegistrationId registrationId = new RegistrationId(student.getId(), trainingClass.getId());
        Registration registration = findRegistration(registrationId);
        registration.setStatus(command.getStatus());

        return registrationRepository.getRegistrationOfStudentDto(registrationId);
    }
}

