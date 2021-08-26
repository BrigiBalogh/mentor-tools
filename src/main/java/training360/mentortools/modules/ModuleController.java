package training360.mentortools.modules;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import training360.mentortools.students.CreateStudentCommand;
import training360.mentortools.students.StudentDto;
import training360.mentortools.students.StudentService;
import training360.mentortools.students.UpdateStudentCommand;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/modules")
@Tag(name = "Operations on module")
@RequiredArgsConstructor
public class ModuleController {


    private final ModuleService moduleService;



    @GetMapping
    @Operation(summary = "list all modules")
    public List<ModuleDto> getModules() {
        return moduleService.getModules();
    }


    @GetMapping("/{id}")
    @Operation(summary = "get a module")
    @ApiResponse(responseCode = "201", description = "module is  found")
    @ApiResponse(responseCode = "404", description = "module not found by id")
    public ModuleDto findModuleById(@PathVariable("id") long id) {
        return moduleService.findModuleById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a module", description = " New module has been created.")
    @ApiResponse(responseCode = "201", description = "module  is  create")
    @ApiResponse(responseCode = "400", description = "module  is validation exception ")
    public ModuleDto createModule(@Valid @RequestBody CreateModuleCommand command) {
        return moduleService.CreateModule(command);
    }


    @PutMapping("/{id}")
    @Operation(summary = "update a module ", description = " New module  has been update.")
    @ApiResponse(responseCode = "404", description = "module not found by id")
    @ApiResponse(responseCode = "400", description = "module  is validation exception ")
    public ModuleDto updateModuleById(@PathVariable("id") long id,
                                      @Valid @RequestBody UpdateModuleCommand command) {
        return moduleService.updateModuleById(id, command);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete module ", description = " module  has been delete.")
    @ApiResponse(responseCode = "404", description = "module not found by id")
    @ApiResponse(responseCode = "204", description = "module  is has been deleted ")
    public void deleteModule(@PathVariable("id") long id) {

        moduleService.deleteModule(id);
    }

}

