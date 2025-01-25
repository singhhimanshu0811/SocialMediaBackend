package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByCategoryName(@NotBlank(message = "Category name can't be blank.") @Size(min = 5, message = "Category name must contain at least 5 letters.") String categoryName);
    //this method got created by jp itslef!!!!!!!
    //because in createcategory, we used jpa object.findByCategoryName, which is findBy + a variable name in that class!!!!
    /// /////CRRRRRRZZZZZZZZZYYYYYYYYYYYY
}
