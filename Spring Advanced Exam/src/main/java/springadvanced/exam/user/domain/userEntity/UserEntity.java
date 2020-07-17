package springadvanced.exam.user.domain.userEntity;

import springadvanced.exam.cart.domain.Cart;
import springadvanced.exam.user.domain.userRole.UserRole;
import springadvanced.exam.utils.baseClasses.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity()
@Table(name = "users")
public class UserEntity extends BaseEntity {

    private String username;
    private String password;
    private String email;
    private Double personalDiscount;
    private List<UserRole> roles;
    private Cart cart;
    private int totalPurchases;

    public UserEntity() {
    }

    @Column(name = "username", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "personal_discount")
    public Double getPersonalDiscount() {
        return personalDiscount;
    }

    public void setPersonalDiscount(Double personalDiscount) {
        this.personalDiscount = personalDiscount;
    }

    @OneToMany(mappedBy = "user", targetEntity = UserRole.class,
    fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    @OneToOne(mappedBy = "user", targetEntity = Cart.class,
    cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Column(name = "total_purchases")
    public int getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(int totalPurchases) {
        this.totalPurchases = totalPurchases;
    }
}
