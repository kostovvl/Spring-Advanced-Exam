package springadvanced.exam.category.domain;

import org.hibernate.validator.constraints.Length;

public class CategoryAddBinding {

    private String name;
    private String description;

    public CategoryAddBinding() {
    }

    @Length(min = 2, message = "Category name must be at least 2 characters long!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 2, message = "Category name must be at least 2 characters long!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
