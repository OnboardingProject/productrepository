package com.catelog.service;

import java.util.List;

import com.catelog.dto.ProductDTO;
import com.catelog.dto.UpdateDTO;
import com.catelog.entity.Product;

/**
 * Interface for product Service methods
 * 
 *
 */
public interface ProductService {
	
	Product addProduct(ProductDTO productDTO);

	public List<Product> searchByName(String productName);

	Product updateProduct(UpdateDTO updateDTO);

	public Product deleteProduct(String id, String lastUpdatedBy);
	

}
