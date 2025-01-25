package com.ecommerce.project.paylod;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long categoryId;
    @Size(min = 5, message = "Category name must contain at least 5 letters.")
    private String categoryName;
}
