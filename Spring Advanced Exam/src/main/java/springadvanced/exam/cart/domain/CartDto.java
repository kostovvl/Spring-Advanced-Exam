package springadvanced.exam.cart.domain;

import springadvanced.exam.product.domain.ProductDto;
import springadvanced.exam.user.domain.userEntity.UserEntityDto;
import springadvanced.exam.utils.baseClasses.BaseDto;

import java.math.BigDecimal;
import java.util.Map;

public class CartDto extends BaseDto {

    private UserEntityDto user;
    private Map<ProductDto, Integer> products;
    private BigDecimal totalPrice;

    public CartDto() {
    }

    public UserEntityDto getUser() {
        return user;
    }

    public void setUser(UserEntityDto user) {
        this.user = user;
    }

    public Map<ProductDto, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<ProductDto, Integer> products) {
        this.products = products;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
