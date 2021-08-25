package training360.mentortools.students;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training360.mentortools.NotFoundException;
import training360.mentortools.trainingClasses.*;

import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class StudentService {


    private final ModelMapper mapper;

    private final StudentRepository studentRepository;

    public List<StudentDto> getStudents() {

        Type targetListType = new TypeToken<List<StudentDto>>() {
        }.getType();
        return mapper.map(studentRepository.findAll(), targetListType);
    }


    public StudentDto findStudentById(long id) {

        Student student = findStudent(id);

        return mapper.map(student, StudentDto.class);
    }


    public StudentDto createStudent(CreateStudentCommand command) {

        Student student = new Student(command.getName(), command.getEmail(),
                command.getGithubUsername(), command.getComment());
        studentRepository.save(student);
        return mapper.map(student, StudentDto.class);
    }


    @Transactional
    public StudentDto updateStudentById(long id, UpdateStudentCommand command) {
        Student student = findStudent(id);

        student.setName(command.getName());
        student.setEmail(command.getEmail());
        student.setGithubUsername(command.getGithubUsername());
        student.setComment(command.getComment());

        return mapper.map(student, StudentDto.class);
    }

    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }


    public Student findStudent(long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }
}
