package springadvanced.exam.product.domain;

import java.math.BigDecimal;

public class ProductCartView {

    private String title;
    private int quantity;
    private BigDecimal totalPrice;

    public ProductCartView() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
