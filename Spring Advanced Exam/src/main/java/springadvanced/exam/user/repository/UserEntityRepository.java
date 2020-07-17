package springadvanced.exam.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springadvanced.exam.user.domain.userEntity.UserEntity;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

}
