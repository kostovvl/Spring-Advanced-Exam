package springadvanced.exam.product.domain;

import springadvanced.exam.category.domain.Category;
import springadvanced.exam.utils.baseClasses.BaseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDto extends BaseDto {

    private String title;
    private String description;
    private Category category;
    private LocalDateTime addedOn;
    private LocalDateTime LastUpdated;
    private String pictureUrl;
    private BigDecimal price;
    private Double maxDiscountPercent;
    private Double AdminDiscount;
    private int numberOfPurchases;

    public ProductDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    public LocalDateTime getLastUpdated() {
        return LastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        LastUpdated = lastUpdated;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Double getMaxDiscountPercent() {
        return maxDiscountPercent;
    }

    public void setMaxDiscountPercent(Double maxDiscountPercent) {
        this.maxDiscountPercent = maxDiscountPercent;
    }

    public Double getAdminDiscount() {
        return AdminDiscount;
    }

    public void setAdminDiscount(Double adminDiscount) {
        AdminDiscount = adminDiscount;
    }

    public int getNumberOfPurchases() {
        return numberOfPurchases;
    }

    public void setNumberOfPurchases(int numberOfPurchases) {
        this.numberOfPurchases = numberOfPurchases;
    }
}
