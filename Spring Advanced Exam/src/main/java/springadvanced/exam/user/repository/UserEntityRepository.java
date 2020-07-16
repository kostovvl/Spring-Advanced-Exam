package springadvanced.exam.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springadvanced.exam.user.domain.userEntity.UserEntity;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, String> {
}
