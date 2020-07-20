package springadvanced.exam.category.domain;

import org.hibernate.validator.constraints.Length;

public class CategoryAddBinding {

    private String name;

    public CategoryAddBinding() {
    }

    @Length(min = 2, message = "Category name must be at least 2 characters long!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
