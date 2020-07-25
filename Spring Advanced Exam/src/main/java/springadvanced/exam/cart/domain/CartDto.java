package springadvanced.exam.cart.domain;

import springadvanced.exam.product.domain.ProductDto;
import springadvanced.exam.user.domain.userEntity.UserEntityDto;
import springadvanced.exam.utils.baseClasses.BaseDto;

import java.math.BigDecimal;
import java.util.Map;

public class CartDto extends BaseDto {

    private UserEntityDto user;
    private Map<String, Integer> products;

    public CartDto() {
    }

    public UserEntityDto getUser() {
        return user;
    }

    public void setUser(UserEntityDto user) {
        this.user = user;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }

    public void addProduct(String productName) {
        if (!this.products.containsKey(productName)){
            this.products.put(productName, 1);
        } else {

            this.products.put(productName, products.get(productName) + 1);
        }
    }
}
