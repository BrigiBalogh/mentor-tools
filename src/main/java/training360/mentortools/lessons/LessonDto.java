package training360.mentortools.lessons;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {

    private  Long id;
    private String title;
    private String url;
}
