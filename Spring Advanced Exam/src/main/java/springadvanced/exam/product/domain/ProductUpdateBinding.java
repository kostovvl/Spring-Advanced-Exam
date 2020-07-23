package springadvanced.exam.product.domain;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class ProductUpdateBinding {

    private String id;
    private String title;
    private String description;
    private String category;
    private String pictureUrl;
    private BigDecimal price;
    private Integer maxDiscountPercent;
    private Integer adminDiscountPercent;

    public ProductUpdateBinding() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Length(min = 2, message = "Product title must be at least 2 characters long!")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Length(min = 10, message = "Product description must be at least 2 characters long!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    @NotBlank(message = "Picture Url can not be blank!")
    // @URL(message = "Enter valid picture Url")
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @DecimalMin(value = "0", message = "Price can not be negative number!")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Max(value = 100, message = "Discount percent can not be more than 100!")
    @Min(value = 0, message = "Discount percent can not be negative number!")
    public Integer getMaxDiscountPercent() {
        return maxDiscountPercent;
    }

    public void setMaxDiscountPercent(Integer maxDiscountPercent) {
        this.maxDiscountPercent = maxDiscountPercent;
    }

    @Max(value = 100, message = "Discount percent can not be more than 100!")
    @Min(value = 0, message = "Discount percent can not be negative number!")
    public Integer getAdminDiscountPercent() {
        return adminDiscountPercent;
    }

    public void setAdminDiscountPercent(Integer adminDiscountPercent) {
        this.adminDiscountPercent = adminDiscountPercent;
    }
}
