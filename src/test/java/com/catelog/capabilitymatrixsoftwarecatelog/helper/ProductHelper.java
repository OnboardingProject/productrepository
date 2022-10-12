package com.catelog.capabilitymatrixsoftwarecatelog.helper;

import java.time.LocalDateTime;
import java.util.Arrays;

import com.catelog.dto.ProductDTO;
import com.catelog.entity.Product;

public class ProductHelper {
	
	public static  Product getProduct() {
	    Product product=new Product();
	    product.setProductId("productId");
	    product.setLastUpdatedBy("user1");
	    return product;
	    
	}
	public static ProductDTO setProductDTO() {
	        ProductDTO productDTO = new ProductDTO();
	       
	        productDTO.setProductName("Azure");
	        productDTO.setProductDescription("Description about the project");
	        productDTO.setContractSpend(10F);
	        productDTO.setStakeholder(7);
	        productDTO.setCategoryLevel(Arrays.asList("1-1-1"));
	        productDTO.setCategoryDescription(Arrays.asList("detailed description"));
	        productDTO.setCreatedBy("user1");
	        productDTO.setDeleted(false);
	        return productDTO;
	}
}
