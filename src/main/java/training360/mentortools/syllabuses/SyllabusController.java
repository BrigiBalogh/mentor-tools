package training360.mentortools.syllabuses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/syllabuses")
@Tag(name = "Operations on syllabuses")
@RequiredArgsConstructor
public class SyllabusController {


    private final SyllabusService syllabusService;


    @GetMapping
    @Operation(summary = "list all syllabus ")
    public List<SyllabusDto> getSyllabuses() {
        return syllabusService.getSyllabuses();
    }


    @GetMapping("/{id}")
    @Operation(summary = "get a syllabus ")
    @ApiResponse(responseCode = "201", description = "syllabus  is  found ")
    @ApiResponse(responseCode = "404", description = "syllabus not found ")
    public SyllabusDto findSyllabusById(@PathVariable("id") long id) {
        return syllabusService.findSyllabusById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a syllabus ", description = " New syllabus  has been created.")
    @ApiResponse(responseCode = "201", description = "syllabus  is  create")
    @ApiResponse(responseCode = "400", description = "syllabus  is validation exception ")
    public SyllabusDto createSyllabus(@Valid @RequestBody CreateSyllabusCommand command) {
        return syllabusService.createSyllabus(command);
    }


    @PutMapping("/{id}")
    @Operation(summary = "update a syllabus ", description = " New syllabus  has been update.")
    @ApiResponse(responseCode = "404", description = "syllabus not found by id")
    @ApiResponse(responseCode = "400", description = "syllabus  is validation exception ")
    public SyllabusDto updateSyllabusById(@PathVariable("id") long id, @Valid @RequestBody UpdateSyllabusCommand command) {
        return syllabusService.updateSyllabusById(id, command);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete a syllabus ", description = " syllabus  has been delete.")
    @ApiResponse(responseCode = "404", description = "syllabus not found by id")
    @ApiResponse(responseCode = "204", description = "syllabus  is has been deleted ")
    public void deleteSyllabus(@PathVariable("id") Long id) {

        syllabusService.deleteSyllabus(id);
    }

}
