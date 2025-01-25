package com.ecommerce.project.paylod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private List<ProductDTO>productContent;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private Long totalElements;//make it long, as pageDetails.getTotalElements returns long only
    private boolean lastPage;
}
