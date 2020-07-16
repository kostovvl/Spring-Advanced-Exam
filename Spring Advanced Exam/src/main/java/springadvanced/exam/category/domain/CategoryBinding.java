package springadvanced.exam.category.domain;

import org.hibernate.validator.constraints.Length;

public class CategoryBinding {

    private String name;

    public CategoryBinding() {
    }

    @Length(min = 2, message = "Category name must be at least 2 characters long!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
