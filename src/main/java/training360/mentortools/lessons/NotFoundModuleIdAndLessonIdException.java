package training360.mentortools.lessons;

import org.zalando.problem.AbstractThrowableProblem;

import java.net.URI;

public class NotFoundModuleIdAndLessonIdException extends AbstractThrowableProblem {


    public NotFoundModuleIdAndLessonIdException(long id, long lessonId) {

        super(URI.create("moduleWithLesson/not-found"),
                "Not found",
                org.zalando.problem.Status.NOT_FOUND,
                String.format("Not found with id '%d' and lessonId '%d'", id,lessonId));
    }
}
