package training360.mentortools.lessoncompletion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Video {

    @Enumerated(EnumType.STRING)
    @Column(name = "video_status")
    private VideoStatus videoStatus;

    @Column(name = "video_date")
    private LocalDate videoDate;
}
