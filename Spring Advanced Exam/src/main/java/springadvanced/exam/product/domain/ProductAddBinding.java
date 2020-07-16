package springadvanced.exam.product.domain;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import springadvanced.exam.category.domain.CategoryBinding;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


public class ProductAddBinding {

    private String title;
    private String description;
    private CategoryBinding category;
    private String pictureUrl;
    private BigDecimal price;
    private Double maxDiscountPercent;

    public ProductAddBinding() {
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
    public CategoryBinding getCategory() {
        return category;
    }

    public void setCategory(CategoryBinding category) {
        this.category = category;
    }


    @NotBlank(message = "Picture Url can not be blank!")
    @URL(message = "Enter valid picture Url")
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

    @Max(value = 100, message = "Deiscount percent can not be more than 100!")
    public double getMaxDiscountPercent() {
        return maxDiscountPercent;
    }

    public void setMaxDiscountPercent(double maxDiscountPercent) {
        this.maxDiscountPercent = maxDiscountPercent;
    }
}