package training360.mentortools.registration;

import org.zalando.problem.AbstractThrowableProblem;

import java.net.URI;

public class RegistrationNotFoundException extends AbstractThrowableProblem {

    public RegistrationNotFoundException(Long trainingClassId, Long studentId) {

        super(URI.create("mentortools-registration/not-found"),
                "Not found",
                org.zalando.problem.Status.NOT_FOUND,
                String.format("Not found with trainingClassId '%d' and studentId '%d'",
                        trainingClassId, studentId));
    }
}
