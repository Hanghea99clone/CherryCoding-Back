package clone.cherrycoding.repository;

import clone.cherrycoding.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findAllByOrderByCreatedAtDesc();
    Optional<Lecture> findByIdAndUserId(Long id, Long userId);
}
