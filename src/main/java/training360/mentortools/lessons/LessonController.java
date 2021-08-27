package training360.mentortools.lessons;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/modules/{id}/lessons")
@Tag(name = "Operations on lesson")
@RequiredArgsConstructor
public class LessonController {


    private final LessonService lessonService;



    @GetMapping
    @Operation(summary = "list all lessons of module")
    @ApiResponse(responseCode = "404", description = "module not found by id")
    public List<LessonDto> getLessons(@PathVariable Long id) {
        return lessonService.getLessons(id);
    }


    @GetMapping("/{lessons-id}")
    @Operation(summary = "get a lesson of module")
    @ApiResponse(responseCode = "201", description = "lesson  is  found ")
    @ApiResponse(responseCode = "404", description = "lesson not found ")
    public LessonDto findLessonById(
            @PathVariable("id") long id,
            @PathVariable("lesson_id")Long lessonId) {
        return lessonService.findLessonById(id, lessonId);
    }


    @PostMapping
    @Operation(summary = "Creates a lesson", description = " New lesson has been created.")
    @ApiResponse(responseCode = "201", description = "lesson is  create")
    @ApiResponse(responseCode = "404", description = "module not found by id")
    public LessonDto createLesson(
            @PathVariable Long id,
            @Valid @RequestBody CreateLessonCommand command) {
        return lessonService.createLesson(id, command);
    }


    @PutMapping("/{lessons-id}")
    @Operation(summary = "update a lesson of module ", description = " New lesson  has been update.")
    @ApiResponse(responseCode = "404", description = "module not found by id")
    @ApiResponse(responseCode = "404", description = "lesson not found by id")
    @ApiResponse(responseCode = "400", description = "module  is validation exception ")
    public LessonDto updateLessonById(
            @PathVariable("id") long id,
            @PathVariable("lesson_id")Long lessonId,
            @Valid @RequestBody UpdateLessonCommand command) {
        return lessonService.UpdateLessonById(id, lessonId, command);
    }


    @DeleteMapping("/{lessons-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete lesson ", description = " lesson  has been delete.")
    @ApiResponse(responseCode = "404", description = "module not found by id")
    @ApiResponse(responseCode = "204", description = "lesson  is has been deleted ")
    public void deleteLesson(
            @PathVariable("id") long id,
            @PathVariable("lesson_id") Long lessonId) {
        lessonService.deleteLesson(id, lessonId);
    }

}
