package springadvanced.exam.user.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springadvanced.exam.user.domain.userRole.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
}
