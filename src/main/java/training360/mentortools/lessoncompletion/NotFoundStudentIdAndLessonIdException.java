package training360.mentortools.lessoncompletion;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class NotFoundStudentIdAndLessonIdException extends AbstractThrowableProblem {


    public NotFoundStudentIdAndLessonIdException(long id, long lessonId) {
        super(URI.create("lessoncompletion/not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("Not found with id '%d' and lessonId '%d'", id, lessonId));

    }
}
