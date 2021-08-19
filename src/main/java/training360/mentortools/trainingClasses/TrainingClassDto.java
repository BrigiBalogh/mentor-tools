package training360.mentortools.trainingClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingClassDto {

    private Long id;

    private String name;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
