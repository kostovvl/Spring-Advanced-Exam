package springadvanced.exam.category.domain;

import springadvanced.exam.product.domain.ProductDto;
import springadvanced.exam.utils.baseClasses.BaseDto;

import java.util.List;

public class CategoryDto extends BaseDto {

    private String name;
    private List<ProductDto> products;

    public CategoryDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }
}
