package springadvanced.exam.cart.domain;

import springadvanced.exam.product.domain.Product;
import springadvanced.exam.user.domain.UserEntity;
import springadvanced.exam.utils.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {

    private UserEntity user;
    private Map<Product, Integer> products;
    private BigDecimal totalPrice;

    public Cart() {
    }

    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @ElementCollection()
    @CollectionTable(name = "user_cart_products")
    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    @Column(name = "total_price")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
