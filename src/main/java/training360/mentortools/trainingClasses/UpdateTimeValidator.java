package training360.mentortools.trainingClasses;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UpdateTimeValidator implements ConstraintValidator<IsValidDate, UpdateTrainingClassCommand> {

    @Override
    public boolean isValid(UpdateTrainingClassCommand command, ConstraintValidatorContext context) {
        return command.getStartDate() != null && command.getEndDate() != null &&
                command.getStartDate().isBefore(command.getEndDate()) ;
    }
}
