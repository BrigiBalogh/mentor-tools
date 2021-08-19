package training360.mentortools.registration;

import org.springframework.data.jpa.repository.JpaRepository;
import training360.mentortools.students.Student;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
