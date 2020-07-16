package springadvanced.exam.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springadvanced.exam.product.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
