package springadvanced.exam.category.domain;

import springadvanced.exam.product.domain.ProductDto;
import springadvanced.exam.utils.baseClasses.BaseDto;

import java.util.List;

public class CategoryDto extends BaseDto {

    private String id;
    private String name;
    private String description;
    private List<ProductDto> products;
    private int numberOfProducts;

    public CategoryDto() {
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }
}
