package springadvanced.exam.integrationTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import springadvanced.exam.category.domain.Category;
import springadvanced.exam.category.repository.CategoryRepository;

import java.util.ArrayList;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        categoryRepository.deleteAll();

        Category category = new Category();
        category.setName("Terlici");
        category.setDescription("Descrtiption for category Terlici.");
        category.setProducts(new ArrayList<>());

        this.categoryRepository.saveAndFlush(category);
    }

    @Test
    public void should_Return_Category_Create_Page() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/root-admin/categories/create").
                        with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("category/category-create"))
                .andExpect(model().attributeExists("categoryCreate"));

    }

    @Test
    public void should_Create_New_Category() throws Exception {
        //act
        mockMvc.perform(MockMvcRequestBuilders
        .post("/root-admin/categories/create")
                .with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN"))
                .with(csrf())
                .param("name", "Shushoni")
                .param("description", "Description of shushoni")
        ).andExpect(status().is3xxRedirection());

        //assert
        Assertions.assertEquals(2, this.categoryRepository.count());
    }

    @Test
    public void should_Not_Create_New_Category_Because_Wrong_Params() throws Exception {
        //act
        mockMvc.perform(MockMvcRequestBuilders
                .post("/root-admin/categories/create")
                .with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN"))
                .with(csrf())
                .param("name", "Shushoni")
                .param("description", "shrt")
        ).andExpect(status().is3xxRedirection());

        //assert
        Assertions.assertEquals(1, this.categoryRepository.count());
    }

    @Test
    public void should_Update_Existing_Category() throws Exception {
        //arrange
        String id = this.categoryRepository.findByName("Terlici").orElse(null).getId();

        String name = "Shushoni";
        String description = "Description of Shushoni";

        //act
        mockMvc.perform(MockMvcRequestBuilders
                .post("/root-admin/categories/update")
                .with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN"))
                .with(csrf())
                .param("id", id)
                .param("name", name)
                .param("description", description)
        ).andExpect(status().is3xxRedirection());

        //assert
        Category updated = this.categoryRepository.findById(id).orElse(null);
        Assertions.assertEquals(id, updated.getId());
        Assertions.assertEquals(name, updated.getName());
        Assertions.assertEquals(description, updated.getDescription());
    }

    @Test
    public void should_Not_Update_Existing_Category_Because_Wrong_Params() throws Exception {
        //arrange
        Category originalCategory = this.categoryRepository.findByName("Terlici").orElse(null);

        String name = "Shushoni";
        String description = "shrt";

        //act
        mockMvc.perform(MockMvcRequestBuilders
                .post("/root-admin/categories/update")
                .with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN"))
                .with(csrf())
                .param("id", originalCategory.getId())
                .param("name", name)
                .param("description", description)
        ).andExpect(status().is3xxRedirection());

        //assert
        Category updatedCategory = this.categoryRepository.findById(originalCategory.getId()).orElse(null);

        Assertions.assertEquals(originalCategory.getId(), updatedCategory.getId());
        Assertions.assertEquals(originalCategory.getName(), updatedCategory.getName());
        Assertions.assertEquals(originalCategory.getDescription(), updatedCategory.getDescription());
    }



    @Test
    public void should_Delete_Category() throws Exception {
        //arrange
        String id = this.categoryRepository.findByName("Terlici").orElse(null).getId();

        //act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/categories/delete").param("id", id).
                        with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin"));

        //assert
        Assertions.assertEquals(0, this.categoryRepository.count());
    }

    @Test
    public void should_Return_Update_Category_Page() throws Exception {
        //arrange
        String id = this.categoryRepository.findByName("Terlici").orElse(null).getId();

        //act
        mockMvc.perform(MockMvcRequestBuilders
                .get("/admin/categories/update").param("id", id).
                        with(user("Pesho").password("123").roles("USER", "ADMIN", "ROOT_ADMIN")))
                .andExpect(status().isOk())
                .andExpect(view().name("category/category-update"))
                .andExpect(model().attributeExists("categoryUpdate"));
    }

}
