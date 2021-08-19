package training360.mentortools.trainingClasses;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CreateDateValidator implements ConstraintValidator <IsValidDate, CreateTrainingClassCommand>{

   ;

    @Override
    public boolean isValid(CreateTrainingClassCommand command, ConstraintValidatorContext context) {
        return command.getStartDate() != null && command.getEndDate() != null &&
                command.getStartDate().isBefore(command.getEndDate()) ;
    }
}
