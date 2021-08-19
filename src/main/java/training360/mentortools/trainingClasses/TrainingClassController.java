package training360.mentortools.trainingClasses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/training-classes")
@Tag(name = "Operations on training class")
@RequiredArgsConstructor
public class TrainingClassController {


    private final TrainingClassService trainingClassService;



    @GetMapping
    @Operation(summary = "list all training classes")
    public List<TrainingClassDto> getTrainingClasses() {
        return trainingClassService.getTrainingClasses();
    }


    @GetMapping("/{id}")
    @Operation(summary = "get a training class")
    @ApiResponse(responseCode = "201", description = "training class is  found")
    public TrainingClassDto findTrainingClassById(@PathVariable("id") long id) {
        return trainingClassService.findTrainingClassById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a training class", description = " New training class has been created.")
    @ApiResponse(responseCode = "201", description = "training class is  create")
    @ApiResponse(responseCode = "400", description = "training class is validation exception ")
    public TrainingClassDto createTrainingClass(@Valid @RequestBody CreateTrainingClassCommand command) {
        return trainingClassService.createTrainingClass(command);
    }


    @PutMapping("/{id}")
    @Operation(summary = "update a training class", description = " New training class has been update.")
    @ApiResponse(responseCode = "404", description = "trainingClass not found by id")
    @ApiResponse(responseCode = "400", description = "training class is validation exception ")
    public TrainingClassDto updateTrainingClassById(@PathVariable("id") long id, @Valid @RequestBody UpdateTrainingClassCommand command) {
        return trainingClassService.updateTrainingClassById(id, command);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete training class", description = " Training class has been delete.")
    @ApiResponse(responseCode = "404", description = "trainingClass not found by id")
    @ApiResponse(responseCode = "204", description = "training class is has been deleted ")
    public void deleteTrainingClass(@PathVariable("id") long id) {

        trainingClassService.deleteTrainingClass(id);
    }
}


