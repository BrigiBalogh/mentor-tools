package training360.mentortools.lessoncompletion;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import training360.mentortools.lessons.CreateLessonCommand;
import training360.mentortools.lessons.LessonDto;
import training360.mentortools.lessons.LessonService;
import training360.mentortools.lessons.UpdateLessonCommand;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/students/{id}/lessoncompletions")
@Tag(name = "Operations on lesson completion")
@RequiredArgsConstructor
public class LessonCompletionController {


    private final LessonCompletionService lessonCompletionService;


    @GetMapping
    @Operation(summary = "list all lessons completion")
    @ApiResponse(responseCode = "404", description = "student not found by id")
    public List<LessonCompletionDto> getLessonsWithCompletions(@PathVariable Long id) {
        return lessonCompletionService.getLessonsWithCompletions(id);
    }


    @GetMapping("/{lesson-id}")
    @Operation(summary = "get a lesson completion")
    @ApiResponse(responseCode = "201", description = "lesson completion is  found ")
    @ApiResponse(responseCode = "404", description = "lesson completion not found ")
    public LessonCompletionDto findLessonWithCompletionById(
            @PathVariable("id") long id,
            @PathVariable("lesson_id") Long lessonId) {
        return lessonCompletionService.findLessonWithCompletionById(id, lessonId);
    }


    @PostMapping
    @Operation(summary = "Creates a lesson completion", description = " New lesson completion has been created.")
    @ApiResponse(responseCode = "201", description = "lesson completion is  create")
    @ApiResponse(responseCode = "404", description = "lesson not found by id")
    public LessonCompletionDto createLessonCompletion(
            @PathVariable Long id,
            @Valid @RequestBody CreateLessonCompletionCommand command) {
        return lessonCompletionService.createLessonCompletion(id, command);
    }


    @PutMapping
    @Operation(summary = "update a lesson completion ", description = " New lesson completion has been update.")
    @ApiResponse(responseCode = "404", description = "student not found by id")
    @ApiResponse(responseCode = "404", description = "lesson not found by id")
    @ApiResponse(responseCode = "400", description = "lesson  is validation exception ")
    public LessonCompletionDto updateLessonCompletionById(
            @PathVariable("id") long id,
            @Valid @RequestBody UpdateLessonCompletionCommand command) {
        return lessonCompletionService.UpdateLessonCompletionById(id, command);
    }


    @DeleteMapping("/{lesson-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete lesson completion ", description = " lesson completion has been delete.")
    @ApiResponse(responseCode = "404", description = "lesson completion not found by id")
    @ApiResponse(responseCode = "204", description = "lesson  is has been deleted ")
    public void deleteLessonCompletion(
            @PathVariable("id") long id,
            @PathVariable("lesson_id") Long lessonId) {
        lessonCompletionService.deleteLessonCompletion(id, lessonId);
    }
}