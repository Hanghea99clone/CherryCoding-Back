package clone.cherrycoding.repository;

import clone.cherrycoding.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
