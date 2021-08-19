package training360.mentortools;

import org.zalando.problem.AbstractThrowableProblem;

import java.net.URI;

public class NotFoundException extends AbstractThrowableProblem {



    public NotFoundException(long id) {
        super(URI.create("mentortools/not-found"),
                "Not found",
                org.zalando.problem.Status.NOT_FOUND,
                String.format("Not found with id '%d'", id));
    }
}
