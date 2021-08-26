package training360.mentortools.syllabuses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training360.mentortools.modules.ModuleDto;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyllabusWithModuleDto {

    private Long id;
    private String name;
    public Set<ModuleDto> modules;
}
