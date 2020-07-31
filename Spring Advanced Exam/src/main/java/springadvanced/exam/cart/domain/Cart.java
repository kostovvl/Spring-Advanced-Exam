package springadvanced.exam.cart.domain;

import springadvanced.exam.product.domain.Product;
import springadvanced.exam.user.domain.userEntity.UserEntity;
import springadvanced.exam.utils.baseClasses.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {

    private UserEntity user;
    private Map<String, Integer> products;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_cart_products")
    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }


}
