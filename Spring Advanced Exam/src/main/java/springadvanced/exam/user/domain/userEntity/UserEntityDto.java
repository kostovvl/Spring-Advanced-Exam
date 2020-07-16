package springadvanced.exam.user.domain.userEntity;
import springadvanced.exam.cart.domain.CartDto;
import springadvanced.exam.user.domain.userRole.UserRoleDto;
import springadvanced.exam.utils.baseClasses.BaseDto;

import java.util.List;

public class UserEntityDto extends BaseDto {

    private String username;
    private String password;
    private String email;
    private Double personalDiscount;
    private List<UserRoleDto> roles;
    private CartDto cart;

    public UserEntityDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getPersonalDiscount() {
        return personalDiscount;
    }

    public void setPersonalDiscount(Double personalDiscount) {
        this.personalDiscount = personalDiscount;
    }

    public List<UserRoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleDto> roles) {
        this.roles = roles;
    }

    public CartDto getCart() {
        return cart;
    }

    public void setCart(CartDto cart) {
        this.cart = cart;
    }
}