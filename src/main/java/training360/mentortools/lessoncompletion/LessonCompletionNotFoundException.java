package training360.mentortools.lessoncompletion;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class LessonCompletionNotFoundException extends AbstractThrowableProblem {


    public LessonCompletionNotFoundException(LessonCompletionId id) {
        super(URI.create("lessoncompletion-with-id/not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("Not found with id '%d' and lessonId '%d'", id.getStudentId(), id.getLessonId()));

    }
}
