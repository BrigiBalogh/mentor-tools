package training360.mentortools.registration;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
@Tag(name = "Operations on registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping("/training-classes/{id}/registrations")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a registration with training class", description = " New registration has been created with training class id.")
    @ApiResponse(responseCode = "404", description = "student not found by id")
    @ApiResponse(responseCode = "404", description = "training class not found by id")
    @ApiResponse(responseCode = "201", description = "registration  is  create")
    @ApiResponse(responseCode = "400", description = "registration  is validation exception ")
    public RegistrationOfStudentDto createRegistration(@PathVariable Long id, @Valid @RequestBody CreateRegistrationCommand command) {
        return registrationService.createRegistration(id, command);
    }


    @GetMapping("/training-classes/{id}/registrations")
    @Operation(summary = "list all registration by training class")
    public List<RegistrationOfStudentDto> getRegistrationByTrainingClass(@PathVariable("id") Long id) {
        return registrationService.getRegistrationByTrainingClass(id);
    }

    @GetMapping("/students/{id}/registrations")
    @Operation(summary = "get training classes by student ")
    @ApiResponse(responseCode = "201", description = "registration is  found")
    public List<RegistrationOfTrainingClassDto> registrationByStudent(@PathVariable("id") long id) {
        return registrationService.registrationByStudent(id);
    }


    @PutMapping("/training-classes/{id}/registrations")
    @Operation(summary = "Update a registration ", description = "  registration has been updated ")
    @ApiResponse(responseCode = "404", description = "student not found by id")
    @ApiResponse(responseCode = "404", description = "training class not found by id")
    @ApiResponse(responseCode = "400", description = "registration  is validation exception ")
    public RegistrationOfStudentDto updateRegistration(@PathVariable("id") long id,
                                                       @Valid @RequestBody UpdateRegistrationCommand command) {
        return registrationService.updateRegistration(id, command);
    }
}