package clone.cherrycoding.repository;

import clone.cherrycoding.entity.Enroll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollRepository extends JpaRepository<Enroll, Long> {

    Optional<Enroll> findByLectureIdAndUserId(Long lectureId, Long userId);

    List<Enroll> findAllByUserId(Long userId);

    void deleteEnrollByLectureIdAndUserId(Long lectureId, Long userId);

    Page<Enroll> findByUserId(Long userId, Pageable pageable);
}
