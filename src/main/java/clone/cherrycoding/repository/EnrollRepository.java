package clone.cherrycoding.repository;

import clone.cherrycoding.entity.Enroll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollRepository extends JpaRepository<Enroll, Long> {

    Optional<Enroll> findByLectureIdAndUserId(Long lectureId, Long userId);

    void deleteEnrollByLectureIdAndUserId(Long lectureId, Long userId);
}
