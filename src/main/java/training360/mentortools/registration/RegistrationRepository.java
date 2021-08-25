package training360.mentortools.registration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    @Query("select new training360.mentortools.registration.RegistrationOfStudentDto(" +
            "r.student.id, r.student.name, r.status)" +
            "from Registration r where r.trainingClass.id = :id")
    List<RegistrationOfStudentDto> getRegistrationsByTrainingClassId(Long id);


    @Query("select new training360.mentortools.registration.RegistrationOfTrainingClassDto(" +
            "r.trainingClass.id, r.trainingClass.name)" +
            "from Registration r where r.student.id = :studentId")
    List<RegistrationOfTrainingClassDto> getRegistrationsByStudentId(Long studentId);

    @Query("select new training360.mentortools.registration.RegistrationOfStudentDto(" +
            "r.student.id, r.student.name,  r.status)from Registration r where r.registrationId = :registrationId ")
    RegistrationOfStudentDto getRegistrationOfStudentDto(RegistrationId registrationId);


    Optional<Registration> findRegistrationByRegistrationId(RegistrationId registrationId);

}

