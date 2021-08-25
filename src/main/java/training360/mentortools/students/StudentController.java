package training360.mentortools.students;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Operations on student")
@RequiredArgsConstructor
public class StudentController {


    private final StudentService studentService;



    @GetMapping
    @Operation(summary = "list all students")
    public List<StudentDto> getStudents() {
        return studentService.getStudents();
    }


    @GetMapping("/{id}")
    @Operation(summary = "get a student")
    @ApiResponse(responseCode = "201", description = "student is  found")
    @ApiResponse(responseCode = "404", description = "student not found by id")
    public StudentDto findStudentById(@PathVariable("id") long id) {
        return studentService.findStudentById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a student", description = " New student has been created.")
    @ApiResponse(responseCode = "201", description = "student  is  create")
    @ApiResponse(responseCode = "400", description = "student  is validation exception ")
    public StudentDto createStudent(@Valid @RequestBody CreateStudentCommand command) {
        return studentService.createStudent(command);
    }


    @PutMapping("/{id}")
    @Operation(summary = "update a student ", description = " New student  has been update.")
    @ApiResponse(responseCode = "404", description = "student not found by id")
    @ApiResponse(responseCode = "400", description = "student  is validation exception ")
    public StudentDto updateStudentById(@PathVariable("id") long id, @Valid @RequestBody UpdateStudentCommand command) {
        return studentService.updateStudentById(id, command);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete student ", description = " student  has been delete.")
    @ApiResponse(responseCode = "404", description = "student not found by id")
    @ApiResponse(responseCode = "204", description = "student  is has been deleted ")
    public void deleteStudent(@PathVariable("id") long id) {

        studentService.deleteStudent(id);
    }
}
