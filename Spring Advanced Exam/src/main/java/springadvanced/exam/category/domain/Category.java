package springadvanced.exam.category.domain;

import springadvanced.exam.product.domain.Product;
import springadvanced.exam.utils.baseClasses.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    private String name;
    private List<Product> products;

    public Category() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "category", targetEntity = Product.class,
    fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
