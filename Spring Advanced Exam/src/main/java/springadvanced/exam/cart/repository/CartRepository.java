package springadvanced.exam.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springadvanced.exam.cart.domain.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
}
