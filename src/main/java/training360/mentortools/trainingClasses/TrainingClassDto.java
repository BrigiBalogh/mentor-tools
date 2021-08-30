package training360.mentortools.trainingClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training360.mentortools.syllabuses.SyllabusDto;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingClassDto {

    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private SyllabusDto syllabus;

}
